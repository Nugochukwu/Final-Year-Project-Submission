package inventorymanagementsystemv_01;

import java.sql.*;

public class DashboardSettings {

    // Stat card visibility
    public static boolean showTotalItems   = true;
    public static boolean showLowStock     = true;
    public static boolean showItemTypes    = true;
    public static boolean showAddedToday   = true;

    // Table visibility
    public static boolean showLowStockTbl  = true;
    public static boolean showRecentTbl    = true;
    public static boolean showInventoryTbl = false;
    public static boolean showLendingTbl   = false;
    public static boolean showRemovedTbl   = true;

    

    // Load settings for this user from DB — call once on login
        public static void load(int userId) {
            try (Connection con = DatabaseConfig.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM user_settings WHERE userId = ?")) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                showTotalItems   = rs.getInt("showTotalItems")   == 1;
                showLowStock     = rs.getInt("showLowStock")     == 1;
                showItemTypes    = rs.getInt("showItemTypes")    == 1;
                showAddedToday   = rs.getInt("showAddedToday")   == 1;
                showLowStockTbl  = rs.getInt("showLowStockTbl")  == 1;
                showRecentTbl    = rs.getInt("showRecentTbl")    == 1;
                showInventoryTbl = rs.getInt("showInventoryTbl") == 1;
                showLendingTbl   = rs.getInt("showLendingTbl")   == 1;
                showRemovedTbl   = rs.getInt("showRemovedTbl")   == 1;
            } else {
                // No row yet — insert defaults
                save(userId);
            }
        } catch (SQLException e) {
            System.err.println("Settings load failed: " + e.getMessage());
        }
    }

    // Save current settings to DB — call when user clicks Apply
    public static void save(int userId) {
        // SQLite uses INSERT OR REPLACE instead of MySQL's ON DUPLICATE KEY UPDATE
        String sql = "INSERT OR REPLACE INTO user_settings " +
            "(userId, showTotalItems, showLowStock, showItemTypes, showAddedToday, " +
            " showLowStockTbl, showRecentTbl, showInventoryTbl, showLendingTbl) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, showTotalItems   ? 1 : 0);
            ps.setInt(3, showLowStock     ? 1 : 0);
            ps.setInt(4, showItemTypes    ? 1 : 0);
            ps.setInt(5, showAddedToday   ? 1 : 0);
            ps.setInt(6, showLowStockTbl  ? 1 : 0);
            ps.setInt(7, showRecentTbl    ? 1 : 0);
            ps.setInt(8, showInventoryTbl ? 1 : 0);
            ps.setInt(9, showLendingTbl   ? 1 : 0);
            ps.setInt(10, showRemovedTbl  ? 1 : 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Settings save failed: " + e.getMessage());
        }
    }
}