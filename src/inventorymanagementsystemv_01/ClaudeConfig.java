package inventorymanagementsystemv_01;

import java.sql.*;

public class ClaudeConfig {

    private static String apiKey = "";

    public static void load(int userId) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT claudeKey FROM app_config WHERE userId = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String key = rs.getString("claudeKey");
                if (key != null && !key.isBlank()) apiKey = key;
            }
        } catch (SQLException e) {
            System.err.println("ClaudeConfig load failed: " + e.getMessage());
        }
    }

    public static void save(int userId) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "INSERT OR REPLACE INTO app_config " +
                "(userId, claudeKey, geminiKey, githubToken, " +
                " githubRepo, vercelBaseUrl) " +
                "VALUES (?, ?, " +
                "  COALESCE((SELECT geminiKey    FROM app_config WHERE userId=?), '')," +
                "  COALESCE((SELECT githubToken  FROM app_config WHERE userId=?), '')," +
                "  COALESCE((SELECT githubRepo   FROM app_config WHERE userId=?), '')," +
                "  COALESCE((SELECT vercelBaseUrl FROM app_config WHERE userId=?), '')" +
                ")")) {
            ps.setInt(1, userId);
            ps.setString(2, apiKey);
            ps.setInt(3, userId);
            ps.setInt(4, userId);
            ps.setInt(5, userId);
            ps.setInt(6, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ClaudeConfig save failed: " + e.getMessage());
        }
    }

    public static String  getApiKey()         { return apiKey; }
    public static void    setApiKey(String k) { apiKey = k.trim(); }
    public static boolean isConfigured()      { return !apiKey.isBlank(); }
}