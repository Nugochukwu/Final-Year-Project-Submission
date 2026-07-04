package inventorymanagementsystemv_01;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OneDriveManager {

    private static final String GRAPH = "https://graph.microsoft.com/v1.0";

    private static String driveItemUrl(String fileId) {
        String owner = MicrosoftGraphConfig.fileOwnerEmail.trim();
        if (owner.isBlank())
            return GRAPH + "/me/drive/items/" + fileId;
        try {
            return GRAPH + "/users/" +
                URLEncoder.encode(owner, StandardCharsets.UTF_8) +
                "/drive/items/" + fileId;
        } catch (Exception e) {
            return GRAPH + "/users/" + owner + "/drive/items/" + fileId;
        }
    }

    public static boolean syncSignOutSheet(int userId) {
        System.out.println("[OneDrive] Syncing Sign-Out sheet...");
        return syncFormSheet(userId,
            MicrosoftGraphConfig.signOutFileId,
            MicrosoftGraphConfig.signOutSheet,
            "signout_records", "Sign-Out");
    }

    public static boolean syncSignInSheet(int userId) {
        System.out.println("[OneDrive] Syncing Sign-In sheet...");
        return syncFormSheet(userId,
            MicrosoftGraphConfig.signInFileId,
            MicrosoftGraphConfig.signInSheet,
            "signin_records", "Sign-In");
    }

    public static boolean syncAll(int userId) {
        boolean out = syncSignOutSheet(userId);
        boolean in  = syncSignInSheet(userId);
        return out && in;
    }

    private static boolean syncFormSheet(int userId, String fileId,
            String sheetName, String targetTable, String logTag) {
        try {
            if (fileId == null || fileId.isBlank()) {
                System.err.println("[" + logTag + "] File ID not set in Settings.");
                return false;
            }

            ensureValidToken(userId);

            String encodedSheet = URLEncoder.encode(
                    sheetName == null || sheetName.isBlank()
                        ? "Sheet1" : sheetName,
                    StandardCharsets.UTF_8);

            String url = driveItemUrl(fileId) +
                    "/workbook/worksheets/" + encodedSheet + "/usedRange";

            System.out.println("[" + logTag + "] Fetching: " + url);
            String response = graphGet(url);

            if (response == null || response.isBlank()) {
                System.err.println("[" + logTag + "] No data returned from Graph API.");
                return false;
            }

            List<List<String>> rows = parseValuesArray(response);
            if (rows.size() < 2) {
                System.out.println("[" + logTag + "] Sheet is empty.");
                return true;
            }

            List<String> headers = rows.get(0);
            boolean isFormsExport = findCol(headers, "Start time") >= 0
                    || findCol(headers, "Completion time") >= 0;

            if (isFormsExport) {
                return syncFormsExport(userId, rows, headers, targetTable, logTag);
            } else {
                return syncSimpleSheet(userId, rows, headers, targetTable, logTag);
            }

        } catch (Exception e) {
            System.err.println("[" + logTag + "] Sync failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // -------------------------------------------------------
    //  Microsoft Forms export parser
    //
    //  Deduplication key: signoutDate + itemName + studentId(email)
    //  — uniquely identifies one borrowing event per person per item per day
    //
    //  NO DELETE before sync — only additive inserts.
    //  Finalized records are skipped using the finalized flag.
    //  Records already present in the local table are skipped.
    //  Sign-out records are NEVER deleted when finalized —
    //  the finalized = 1 flag prevents them reappearing on next sync.
    // -------------------------------------------------------
    private static boolean syncFormsExport(int userId,
            List<List<String>> rows, List<String> headers,
            String targetTable, String logTag) throws SQLException {

        int startCol  = firstMatch(headers, "Start time", "Completion time");
        int nameCol   = firstMatch(headers, "Name1", "Name");
        int emailCol  = firstMatch(headers, "Email1", "Email");
        int deptCol   = findCol(headers, "Department");
        int levelCol  = findCol(headers, "Level");

        int lastMeta     = max(startCol, nameCol, emailCol, deptCol, levelCol);
        int firstItemCol = lastMeta + 1;

        if (firstItemCol >= headers.size()) {
            System.err.println("[" + logTag + "] No item columns found after metadata.");
            return false;
        }

        System.out.println("[" + logTag + "] Forms format. Item columns: " +
                firstItemCol + " to " + (headers.size() - 1));

        // The date column name differs between the two target tables
        String dateColName = targetTable.equals("signout_records")
                ? "signoutDate" : "signinDate";

        try (Connection con = DatabaseConfig.getConnection()) {
            con.setAutoCommit(false);
            int inserted = 0;

            // --- Dedup check 1: was this already finalized? ---
            // Uses signoutDate + itemName + studentId as the unique key.
            // Note: finalized_lending stores the studentId from the
            // sign-out record, so we match against that directly.
            PreparedStatement finalizedCheckPs = con.prepareStatement(
                "SELECT 1 FROM finalized_lending " +
                "WHERE LOWER(itemName) = LOWER(?) " +
                "  AND signoutDate = ? " +
                "  AND (LOWER(studentId) = LOWER(?) OR studentId IS NULL OR studentId = '') " +
                "  AND userId = ?");

            // --- Dedup check 2: already in local table? ---
            // Prevents duplicate rows from repeated syncs of the same file.
            PreparedStatement dupCheckPs = con.prepareStatement(
                "SELECT 1 FROM " + targetTable + " " +
                "WHERE LOWER(itemName) = LOWER(?) " +
                "  AND " + dateColName + " = ? " +
                "  AND LOWER(studentId) = LOWER(?) " +
                "  AND userId = ?");

            // --- FIXED: correct INSERT SQL for each table ---
            PreparedStatement insSignoutPs = con.prepareStatement(
                "INSERT INTO signout_records " +
                "(itemName, quantity, borrower, studentId, signoutDate, userId) " +
                "VALUES (?,?,?,?,?,?)");

            PreparedStatement insSigninPs = con.prepareStatement(
                "INSERT INTO signin_records " +
                "(itemName, quantity, borrower, studentId, signinDate, userId) " +
                "VALUES (?,?,?,?,?,?)");

            for (int i = 1; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                if (row.isEmpty()) continue;

                String rawDate = getCell(row, startCol);
                String date    = parseDate(rawDate);
                if (date.isBlank()) continue;

                String name      = getCell(row, nameCol);
                String email     = getCell(row, emailCol);
                String dept      = getCell(row, deptCol);
                String level     = getCell(row, levelCol);

                // email is the most stable unique identifier
                String studentId = email.isBlank() ? name : email;

                // Borrower display includes level/dept context
                String borrower = name.isBlank() ? email : name;
                if (!level.isBlank() || !dept.isBlank()) {
                    borrower += " (" + level +
                            (dept.isBlank() ? "" : " — " + dept) + ")";
                }

                for (int c = firstItemCol; c < headers.size(); c++) {
                    if (c >= row.size()) break;

                    String cellVal = getCell(row, c).trim();
                    if (cellVal.isBlank() || cellVal.equals("0")
                            || cellVal.equals("0.0")) continue;

                    int qty = 1;
                    try {
                        qty = (int) Double.parseDouble(cellVal);
                    } catch (NumberFormatException ignored) {}
                    if (qty <= 0) continue;

                    // Strip category prefix from header
                    // e.g. "Electrical Machines.Oscilloscope" -> "Oscilloscope"
                    String fullHeader = headers.get(c);
                    String itemName = (fullHeader != null && fullHeader.contains("."))
                        ? fullHeader.substring(fullHeader.lastIndexOf('.') + 1).trim()
                        : (fullHeader == null ? "Unknown" : fullHeader.trim());

                    // Check 1 — skip if already finalized
                    finalizedCheckPs.setString(1, itemName);
                    finalizedCheckPs.setString(2, date);
                    finalizedCheckPs.setString(3, studentId);
                    finalizedCheckPs.setInt(4, userId);
                    try (ResultSet fRs = finalizedCheckPs.executeQuery()) {
                        if (fRs.next()) {
                            System.out.println("[" + logTag + "] Skipping finalized: "
                                    + itemName + " / " + studentId + " / " + date);
                            continue;
                        }
                    }

                    // Check 2 — skip if already in local table
                    dupCheckPs.setString(1, itemName);
                    dupCheckPs.setString(2, date);
                    dupCheckPs.setString(3, studentId);
                    dupCheckPs.setInt(4, userId);
                    try (ResultSet dRs = dupCheckPs.executeQuery()) {
                        if (dRs.next()) continue;
                    }

                    // Insert into the correct table
                    if (targetTable.equals("signout_records")) {
                        insSignoutPs.setString(1, itemName);
                        insSignoutPs.setInt(2, qty);
                        insSignoutPs.setString(3, borrower);
                        insSignoutPs.setString(4, studentId);
                        insSignoutPs.setString(5, date);
                        insSignoutPs.setInt(6, userId);
                        insSignoutPs.executeUpdate();
                    } else {
                        insSigninPs.setString(1, itemName);
                        insSigninPs.setInt(2, qty);
                        insSigninPs.setString(3, borrower);
                        insSigninPs.setString(4, studentId);
                        insSigninPs.setString(5, date);
                        insSigninPs.setInt(6, userId);
                        insSigninPs.executeUpdate();
                    }
                    inserted++;
                }
            }

            con.commit();
            System.out.println("[" + logTag + "] Synced " + inserted +
                    " new records from " + (rows.size() - 1) + " submissions.");
            return true;
        }
    }

    // -------------------------------------------------------
    //  Simple flat sheet parser
    //  Sign-Out: Date | Item | Quantity | Borrower | Student ID
    //  Sign-In:  Date | Item | Quantity | Borrower | Student ID
    //
    //  Dedup key: date + itemName + borrower
    //  No DELETE — additive only.
    // -------------------------------------------------------
    private static boolean syncSimpleSheet(int userId,
            List<List<String>> rows, List<String> headers,
            String targetTable, String logTag) throws SQLException {

        int dateCol     = firstMatch(headers, "Date", "Start time", "Completion time");
        int itemCol     = firstMatch(headers, "Item", "Name");
        int qtyCol      = firstMatch(headers, "Quantity", "Qty");
        int borrowerCol = findCol(headers, "Borrower");
        int idCol       = firstMatch(headers, "Student ID", "StudentId", "Email", "ID");

        if (dateCol < 0 || itemCol < 0 || borrowerCol < 0) {
            System.err.println("[" + logTag + "] Sheet requires: Date, Item, Borrower.");
            System.err.println("[" + logTag + "] Found headers: " + headers);
            return false;
        }

        String localDateCol = targetTable.equals("signout_records")
                ? "signoutDate" : "signinDate";

        try (Connection con = DatabaseConfig.getConnection()) {
            con.setAutoCommit(false);
            int inserted = 0;

            PreparedStatement finalizedCheckPs = con.prepareStatement(
                "SELECT 1 FROM finalized_lending " +
                "WHERE LOWER(itemName) = LOWER(?) " +
                "  AND signoutDate = ? " +
                "  AND userId = ?");

            PreparedStatement dupCheckPs = con.prepareStatement(
                "SELECT 1 FROM " + targetTable + " " +
                "WHERE LOWER(itemName) = LOWER(?) " +
                "  AND " + localDateCol + " = ? " +
                "  AND LOWER(borrower) = LOWER(?) " +
                "  AND userId = ?");

            for (int i = 1; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                String item = getCell(row, itemCol);
                if (item.isBlank()) continue;

                String date      = parseDate(getCell(row, dateCol));
                String borrower  = getCell(row, borrowerCol);
                String studentId = getCell(row, idCol);
                if (borrower.isBlank()) continue;

                int qty = 1;
                try {
                    qty = (int) Double.parseDouble(getCell(row, qtyCol));
                } catch (Exception ignored) {}

                // Skip if finalized
                finalizedCheckPs.setString(1, item);
                finalizedCheckPs.setString(2, date);
                finalizedCheckPs.setInt(3, userId);
                try (ResultSet fRs = finalizedCheckPs.executeQuery()) {
                    if (fRs.next()) continue;
                }

                // Skip if duplicate
                dupCheckPs.setString(1, item);
                dupCheckPs.setString(2, date);
                dupCheckPs.setString(3, borrower);
                dupCheckPs.setInt(4, userId);
                try (ResultSet dRs = dupCheckPs.executeQuery()) {
                    if (dRs.next()) continue;
                }

                if (targetTable.equals("signout_records")) {
                    try (PreparedStatement ins = con.prepareStatement(
                        "INSERT INTO signout_records " +
                        "(itemName, quantity, borrower, studentId, signoutDate, userId) " +
                        "VALUES (?,?,?,?,?,?)")) {
                        ins.setString(1, item);
                        ins.setInt(2, qty);
                        ins.setString(3, borrower);
                        ins.setString(4, studentId);
                        ins.setString(5, date);
                        ins.setInt(6, userId);
                        ins.executeUpdate();
                        inserted++;
                    }
                } else {
                    try (PreparedStatement ins = con.prepareStatement(
                        "INSERT INTO signin_records " +
                        "(itemName, quantity, borrower, studentId, signinDate, userId) " +
                        "VALUES (?,?,?,?,?,?)")) {
                        ins.setString(1, item);
                        ins.setInt(2, qty);
                        ins.setString(3, borrower);
                        ins.setString(4, studentId);
                        ins.setString(5, date);
                        ins.setInt(6, userId);
                        ins.executeUpdate();
                        inserted++;
                    }
                }
            }

            con.commit();
            System.out.println("[" + logTag + "] Simple sync: " +
                    inserted + " records inserted.");
            return true;
        }
    }

    // -------------------------------------------------------
    //  HTTP helpers
    // -------------------------------------------------------
    static String graphGet(String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Authorization", "Bearer " + MicrosoftGraphConfig.accessToken)
            .header("Accept", "application/json")
            .GET()
            .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401) {
            System.out.println("[OneDrive] Token expired — attempting refresh...");
            MicrosoftGraphConfig.accessToken = "";
            return null;
        }
        if (response.statusCode() != 200) {
            System.err.println("[OneDrive] Graph GET failed [" +
                    response.statusCode() + "]: " + response.body());
            return null;
        }
        return response.body();
    }

    static boolean graphPatch(String url, String body) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Authorization", "Bearer " + MicrosoftGraphConfig.accessToken)
            .header("Content-Type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println("[OneDrive] Graph PATCH failed [" +
                    response.statusCode() + "]: " + response.body());
            return false;
        }
        return true;
    }

    private static void ensureValidToken(int userId) throws Exception {
        if (MicrosoftGraphConfig.accessToken.isBlank()) {
            boolean ok = GraphAuthManager.refreshAccessToken(userId);
            if (!ok) throw new IllegalStateException(
                "Authentication required. Please authenticate in Settings.");
        }
    }

    // -------------------------------------------------------
    //  JSON parser for Graph API usedRange response
    //  Handles nested [[row],[row],...] structure
    // -------------------------------------------------------
    private static List<List<String>> parseValuesArray(String json) {
        List<List<String>> result = new ArrayList<>();
        try {
            int valIdx = json.indexOf("\"values\"");
            if (valIdx < 0) return result;

            int outerStart = json.indexOf("[[", valIdx);
            if (outerStart < 0) return result;

            int depth    = 0;
            int rowStart = -1;

            for (int i = outerStart; i < json.length(); i++) {
                char c = json.charAt(i);
                if (c == '[') {
                    depth++;
                    if (depth == 2) rowStart = i + 1;
                } else if (c == ']') {
                    if (depth == 2 && rowStart >= 0) {
                        result.add(parseRowCells(json.substring(rowStart, i)));
                        rowStart = -1;
                    }
                    depth--;
                    if (depth == 0) break;
                }
            }
        } catch (Exception e) {
            System.err.println("[OneDrive] parseValuesArray error: " + e.getMessage());
        }
        return result;
    }

    private static List<String> parseRowCells(String rowContent) {
        List<String> cells = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rowContent.length(); i++) {
            char c = rowContent.charAt(i);
            if (c == '"') {
                if (i + 1 < rowContent.length() && rowContent.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                cells.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        cells.add(sb.toString().trim());
        return cells;
    }

    // -------------------------------------------------------
    //  Column and cell helpers
    // -------------------------------------------------------
    private static String getCell(List<String> row, int col) {
        if (col < 0 || col >= row.size()) return "";
        String val = row.get(col);
        return val == null ? "" : val.trim();
    }

    private static int findCol(List<String> headers, String name) {
        for (int i = 0; i < headers.size(); i++) {
            String h = headers.get(i);
            if (h != null && h.trim().equalsIgnoreCase(name)) return i;
        }
        return -1;
    }

    private static int firstMatch(List<String> headers, String... candidates) {
        for (String candidate : candidates) {
            int idx = findCol(headers, candidate);
            if (idx >= 0) return idx;
        }
        return -1;
    }

    private static int max(int... values) {
        int m = -1;
        for (int v : values) if (v > m) m = v;
        return m;
    }

    // -------------------------------------------------------
    //  Date parsing
    //  Handles Excel serial numbers and common string formats
    // -------------------------------------------------------
    private static String parseDate(String raw) {
        if (raw == null || raw.isBlank()) return "";

        // Excel serial: days since Dec 30 1899, Java offset = 25569
        try {
            double serial = Double.parseDouble(raw);
            long epochDay = (long) serial - 25569;
            return LocalDate.ofEpochDay(epochDay).toString();
        } catch (NumberFormatException ignored) {}

        String[] formats = {
            "yyyy-MM-dd", "MM/dd/yyyy", "dd/MM/yyyy",
            "M/d/yyyy",   "dd-MM-yyyy", "yyyy/MM/dd",
            "d/M/yyyy",   "MM-dd-yyyy"
        };
        for (String fmt : formats) {
            try {
                return LocalDate.parse(raw.trim(),
                    DateTimeFormatter.ofPattern(fmt)).toString();
            } catch (Exception ignored) {}
        }

        return raw.trim();
    }

    private static String escape(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}