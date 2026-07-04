package inventorymanagementsystemv_01;

import java.sql.*;
import java.util.*;

public class ItemRecommender {

    // Returns top item names for this user's department
    // filtered by what the user is currently typing
    //
    //
    //Currently scraped
    //
    //
    public static List<String> recommend(int userId, String prefix) {
        List<String> results = new ArrayList<>();
        if (prefix == null || prefix.isBlank()) return results;

        String sql =
            "SELECT name, COUNT(*) as freq FROM item_history " +
            "WHERE userId = ? AND LOWER(name) LIKE LOWER(?) " +
            "GROUP BY LOWER(name) ORDER BY freq DESC LIMIT 8";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, prefix + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                results.add(rs.getString("name"));
        } catch (SQLException e) {
            System.err.println("Recommend failed: " + e.getMessage());
        }
        return results;
    }

    // Call this whenever an item is added successfully
    public static void record(int userId, String itemName) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "INSERT INTO item_history (name, department, userId) " +
                "SELECT ?, COALESCE(department,'General'), ? " +
                "FROM users WHERE userId = ?")) {
            ps.setString(1, itemName);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Record failed: " + e.getMessage());
        }
    }
}