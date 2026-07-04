package inventorymanagementsystemv_01;

import java.sql.*;
import java.io.*;
import java.util.Properties;

public class GeminiConfig {

    private static String apiKey = "";
    private static final String FALLBACK_FILE = "config/gemini.properties";

    // Load from DB first, fall back to properties file
    public static void load(int userId) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT geminiKey FROM app_config WHERE userId = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String key = rs.getString("geminiKey");
                if (key != null && !key.isBlank()) {
                    apiKey = key;
                    return;
                }
            }
        } catch (SQLException e) {
            System.err.println("GeminiConfig DB load failed: " + e.getMessage());
        }
        // Fall back to properties file if no DB entry
        loadFromFile();
    }

    // Load without userId (before login)
    public static void load() {
        loadFromFile();
    }

    private static void loadFromFile() {
        try (FileInputStream fis = new FileInputStream(FALLBACK_FILE)) {
            Properties props = new Properties();
            props.load(fis);
            String key = props.getProperty("gemini.api.key", "").trim();
            if (!key.isBlank()) apiKey = key;
        } catch (IOException e) {
            System.err.println("Gemini properties file not found — " +
                               "configure key in Settings.");
        }
    }

    public static void save(int userId) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "INSERT OR REPLACE INTO app_config " +
                "(userId, geminiKey, claudeKey, githubToken, " +
                " githubRepo, vercelBaseUrl) " +
                "VALUES (?, ?, " +
                "  COALESCE((SELECT claudeKey    FROM app_config WHERE userId=?), '')," +
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
            System.err.println("GeminiConfig save failed: " + e.getMessage());
        }
    }

    public static String getApiKey()          { return apiKey; }
    public static void   setApiKey(String k)  { apiKey = k.trim(); }
    public static boolean isConfigured()       { return !apiKey.isBlank(); }
}