package inventorymanagementsystemv_01;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LendingCalculator {

    public static class OutstandingRecord {
        public int    signoutId;
        public String itemName;
        public String borrower;
        public String studentId;
        public int    quantityOut;       // original sign-out qty
        public int    quantityReturned;  // total returned so far
        public int    quantityStillOut;  // out - returned
        public String signoutDate;
        public String lastReturnDate;    // most recent sign-in for this
        public long   daysOut;
        public boolean isOverdue;        // over 14 days
        public boolean hasPartialReturn; // some but not all returned
    }

    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // -------------------------------------------------------
    //  Calculate outstanding records
    //  Each sign-out row is matched against ALL sign-in rows
    //  for the same borrower + item that came AFTER the sign-out
    //  Multiple sign-in rows are summed to get total returned
    // -------------------------------------------------------
    public static List<OutstandingRecord> calculate(int userId) {
        List<OutstandingRecord> results = new ArrayList<>();

        // Get all sign-out rows not yet finalized
        String sql =
            "SELECT so.id, so.itemName, so.borrower, so.studentId, " +
            "       so.quantity AS outQty, so.signoutDate, " +
            "       COALESCE(si.totalReturned, 0) AS returned, " +
            "       si.lastReturn " +
            "FROM signout_records so " +
                
            "LEFT JOIN (" +
            "  SELECT LOWER(studentId) AS skey, " +   // change bkey → skey
            "         LOWER(itemName)  AS ikey, " +
            "         SUM(quantity)    AS totalReturned, " +
            "         MAX(signinDate)  AS lastReturn, " +
            "         userId " +
            "  FROM signin_records " +
            "  GROUP BY LOWER(studentId), LOWER(itemName), userId" +  // was borrower
            ") si ON LOWER(so.studentId) = si.skey " +   // was borrower
            "     AND LOWER(so.itemName) = si.ikey " +
            "     AND si.userId = so.userId " +
            "WHERE so.userId = ? " +
                "AND so.finalized = 0 " +
            "  AND (so.quantity - COALESCE(si.totalReturned, 0)) > 0 " +
            "ORDER BY so.signoutDate ASC";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            LocalDate today = LocalDate.now();

            while (rs.next()) {
                OutstandingRecord rec = new OutstandingRecord();
                rec.signoutId        = rs.getInt("id");
                rec.itemName         = rs.getString("itemName");
                rec.borrower         = rs.getString("borrower");
                rec.studentId        = nullSafe(rs.getString("studentId"));
                rec.quantityOut      = rs.getInt("outQty");
                rec.quantityReturned = rs.getInt("returned");
                rec.quantityStillOut = rec.quantityOut
                                     - rec.quantityReturned;
                rec.signoutDate      = rs.getString("signoutDate");
                rec.lastReturnDate   = rs.getString("lastReturn");
                rec.hasPartialReturn = rec.quantityReturned > 0;

                try {
                    LocalDate outDate = LocalDate.parse(
                            rec.signoutDate, FMT);
                    rec.daysOut  = ChronoUnit.DAYS.between(
                            outDate, today);
                    rec.isOverdue = rec.daysOut > 14;
                } catch (Exception e) {
                    rec.daysOut   = 0;
                    rec.isOverdue = false;
                }

                results.add(rec);
            }
        } catch (SQLException e) {
            System.err.println("Lending calculation failed: "
                    + e.getMessage());
            e.printStackTrace();
        }

        return results;
    }

    // -------------------------------------------------------
    //  Finalize — staff closes the interaction
    //  remainingReturned = qty being returned right now
    //  lostQty = qty confirmed not coming back
    //  reason = why it wasn't fully returned
    // -------------------------------------------------------
    public static boolean finalize(int signoutId, int userId,
        int remainingReturned, int lostQty, String reason) {
    try (Connection con = DatabaseConfig.getConnection()) {
        con.setAutoCommit(false);

        // Get the sign-out record
        PreparedStatement get = con.prepareStatement(
            "SELECT * FROM signout_records WHERE id = ?");
        get.setInt(1, signoutId);
        ResultSet rs = get.executeQuery();
        if (!rs.next()) return false;

        String itemName    = rs.getString("itemName");
        String borrower    = rs.getString("borrower");
        String studentId   = rs.getString("studentId");
        String signoutDate = rs.getString("signoutDate");
        int    outQty      = rs.getInt("quantity");

        long daysOut = 0;
        try {
            java.time.LocalDate out =
                java.time.LocalDate.parse(signoutDate, FMT);
            daysOut = java.time.temporal.ChronoUnit.DAYS
                    .between(out, java.time.LocalDate.now());
        } catch (Exception ignored) {}

        // Get total previously returned via sign-in records
        PreparedStatement prev = con.prepareStatement(
            "SELECT COALESCE(SUM(quantity), 0) AS total " +
            "FROM signin_records " +
            "WHERE LOWER(borrower) LIKE LOWER(?) " +
            "  AND LOWER(itemName) = LOWER(?) " +
            "  AND userId = ?");
        prev.setString(1, "%" +
            (borrower.contains("(")
                ? borrower.substring(0, borrower.indexOf("(")).trim()
                : borrower) + "%");
        prev.setString(2, itemName);
        prev.setInt(3, userId);
        ResultSet pr = prev.executeQuery();
        int previouslyReturned = pr.next() ? pr.getInt("total") : 0;
        int totalReturned = previouslyReturned + remainingReturned;

        // Insert into finalized_lending
        PreparedStatement fin = con.prepareStatement(
            "INSERT INTO finalized_lending " +
            "(itemName, borrower, studentId, quantityOut, " +
            " quantityReturned, quantityLost, signoutDate, " +
            " finalizedDate, finalizationReason, userId) " +
            "VALUES (?,?,?,?,?,?,?,date('now'),?,?)");
        fin.setString(1, itemName);
        fin.setString(2, borrower);
        fin.setString(3, studentId);
        fin.setInt(4, outQty);
        fin.setInt(5, totalReturned);
        fin.setInt(6, lostQty);
        fin.setString(7, signoutDate);
        fin.setString(8, reason);
        fin.setInt(9, userId);
        fin.executeUpdate();

        // Restore returned quantity to inventory
        if (remainingReturned > 0) {
            PreparedStatement restore = con.prepareStatement(
                "UPDATE inventory SET quantity = quantity + ? " +
                "WHERE LOWER(name) = LOWER(?) AND userId = ?");
            restore.setInt(1, remainingReturned);
            restore.setString(2, itemName);
            restore.setInt(3, userId);
            restore.executeUpdate();
        }

        // Mark signout_record as finalized — DO NOT DELETE
        // This prevents it reappearing on next sync
        PreparedStatement mark = con.prepareStatement(
            "UPDATE signout_records SET finalized = 1 " +
            "WHERE id = ?");
        mark.setInt(1, signoutId);
        mark.executeUpdate();

        con.commit();
        return true;

    } catch (Exception e) {
        System.err.println("Finalize failed: " + e.getMessage());
        return false;
    }
}

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }
}