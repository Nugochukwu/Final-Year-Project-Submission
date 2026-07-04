package inventorymanagementsystemv_01;

import java.sql.*;

public class MicrosoftGraphConfig {

    private static final int SHARED_ID = 0;

    public static String clientId        = "";
    public static String clientSecret    = "";
    public static String tenantId        = "";
    public static String fileOwnerEmail  = "";
    public static String signOutFileId   = "";
    public static String signInFileId    = "";
    public static String signOutSheet    = "Sheet1";
    public static String signInSheet     = "Sheet1";
    public static String accessToken     = "";
    public static String refreshToken    = "";

    public static void load(int userId) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT msClientId, msClientSecret, msTenantId, " +
                "msFileOwnerEmail, msSignOutFileId, msSignInFileId, " +
                "msSignOutSheet, msSignInSheet, msRefreshToken " +
                "FROM app_config WHERE userId = ?")) {
            ps.setInt(1, SHARED_ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                clientId       = safe(rs.getString("msClientId"));
                clientSecret   = safe(rs.getString("msClientSecret"));
                tenantId       = safe(rs.getString("msTenantId"));
                fileOwnerEmail = safe(rs.getString("msFileOwnerEmail"));
                signOutFileId  = safe(rs.getString("msSignOutFileId"));
                signInFileId   = safe(rs.getString("msSignInFileId"));
                signOutSheet   = safe(rs.getString("msSignOutSheet"), "Sheet1");
                signInSheet    = safe(rs.getString("msSignInSheet"), "Sheet1");
                refreshToken   = safe(rs.getString("msRefreshToken"));
            }
        } catch (SQLException e) {
            System.err.println("GraphConfig load: " + e.getMessage());
        }
    }

    public static void save(int userId) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "INSERT OR REPLACE INTO app_config " +
                "(userId, msClientId, msClientSecret, msTenantId, " +
                " msFileOwnerEmail, msSignOutFileId, msSignInFileId, " +
                " msSignOutSheet, msSignInSheet, msRefreshToken, " +
                " geminiKey, claudeKey, githubToken, " +
                " githubRepo, vercelBaseUrl, ngrokUrl) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?," +
                "  COALESCE((SELECT geminiKey    FROM app_config WHERE userId=?),'')," +
                "  COALESCE((SELECT claudeKey    FROM app_config WHERE userId=?),'')," +
                "  COALESCE((SELECT githubToken  FROM app_config WHERE userId=?),'')," +
                "  COALESCE((SELECT githubRepo   FROM app_config WHERE userId=?),'')," +
                "  COALESCE((SELECT vercelBaseUrl FROM app_config WHERE userId=?),'')," +
                "  COALESCE((SELECT ngrokUrl     FROM app_config WHERE userId=?),'')" +
                ")")) {
            ps.setInt(1,    SHARED_ID);
            ps.setString(2, clientId);
            ps.setString(3, clientSecret);
            ps.setString(4, tenantId);
            ps.setString(5, fileOwnerEmail);
            ps.setString(6, signOutFileId);
            ps.setString(7, signInFileId);
            ps.setString(8, signOutSheet);
            ps.setString(9, signInSheet);
            ps.setString(10, refreshToken);
            for (int i = 11; i <= 16; i++) ps.setInt(i, SHARED_ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("GraphConfig save: " + e.getMessage());
        }
    }

    public static void saveRefreshToken(int userId, String token) {
        refreshToken = token;
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "UPDATE app_config SET msRefreshToken = ? WHERE userId = ?")) {
            ps.setString(1, token);
            ps.setInt(2, SHARED_ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Token save failed: " + e.getMessage());
        }
    }

    public static boolean isConfigured() {
        return !clientId.isBlank() && !tenantId.isBlank();
    }

    public static boolean isAuthenticated() {
        return !refreshToken.isBlank() || !accessToken.isBlank();
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    private static String safe(String s, String fallback) {
        return (s == null || s.isBlank()) ? fallback : s;
    }
}