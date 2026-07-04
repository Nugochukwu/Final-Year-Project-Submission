package inventorymanagementsystemv_01;

import java.sql.*;

public class GitHubConfig {
    public static String githubToken   = "";
    public static String githubRepo    = ""; // e.g. "YourName/sst-lending-forms"
    public static String vercelBaseUrl = ""; // e.g. "https://sst-forms.vercel.app"

    public static void load(int userId) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT githubToken, githubRepo, vercelBaseUrl " +
                "FROM app_config WHERE userId = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                githubToken   = rs.getString("githubToken");
                githubRepo    = rs.getString("githubRepo");
                vercelBaseUrl = rs.getString("vercelBaseUrl");
            }
        } catch (SQLException e) {
            System.err.println("GitHubConfig load failed: " + e.getMessage());
        }
    }

    public static void save(int userId) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "INSERT OR REPLACE INTO app_config " +
                "(userId, githubToken, githubRepo, vercelBaseUrl) " +
                "VALUES (?,?,?,?)")) {
            ps.setInt(1, userId);
            ps.setString(2, githubToken);
            ps.setString(3, githubRepo);
            ps.setString(4, vercelBaseUrl);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("GitHubConfig save failed: " + e.getMessage());
        }
    }
}