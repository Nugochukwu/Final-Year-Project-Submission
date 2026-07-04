package inventorymanagementsystemv_01;

import java.sql.*;
import java.io.File;

public class DatabaseConfig {

    private static final String DB_PATH;
    static {
    String appData = System.getenv("APPDATA");
    if (appData != null) {
        new java.io.File(appData + "/SSTInventory").mkdirs();
        DB_PATH = appData + "/SSTInventory/inventory.db";
    } else {
        new java.io.File("data").mkdirs();
        DB_PATH = "data/inventory.db";
    }
}

    private static final String URL     = "jdbc:sqlite:" + DB_PATH;

    public static void initialise() {
        
        // Making sure the data folder exists
        new File("data").mkdirs();

        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "  userId   INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  name TEXT NOT NULL UNIQUE," +
                "  role TEXT NOT NULL DEFAULT 'staff',"+
                "  password TEXT NOT NULL," +
                "emailAddress TEXT NOT NULL"+
                ")");

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS inventory (" +
                "  itemId     INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  name       TEXT    NOT NULL," +
                "  type       TEXT    NOT NULL," +
                "  department       TEXT    NOT NULL," +
                "  description       TEXT DEFAULT ''," +
                "  quantity   INTEGER NOT NULL DEFAULT 0," +
                "  userId     INTEGER NOT NULL," +
                "  updated_at DATETIME DEFAULT (datetime('now'))," +
                "  FOREIGN KEY (userId) REFERENCES users(userId)" +
                ")");
            
            st.executeUpdate(
                "CREATE TRIGGER IF NOT EXISTS update_inventory_timestamp " +
                "AFTER UPDATE ON inventory FOR EACH ROW " +
                "BEGIN " +
                "  UPDATE inventory SET updated_at = datetime('now') " +
                "  WHERE itemId = OLD.itemId; " +
                "END");
            
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS item_history (" +
                "  id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  name       TEXT NOT NULL," +
                "  department TEXT NOT NULL," +
                "  userId     INTEGER," +
                "  FOREIGN KEY (userId) REFERENCES users(userId)" +
                ")");
            
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS lending (" +
                "  id          INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  item_id     INTEGER NOT NULL," +
                "  item_name   TEXT    NOT NULL," +
                "  quantity    INTEGER NOT NULL DEFAULT 1," +
                "  reason      TEXT," +
                "  borrower    TEXT," +
                "  lent_date   DATETIME DEFAULT (datetime('now'))," +
                "  return_date TEXT," +
                "  returned    INTEGER DEFAULT 0," +
                "  userId      INTEGER," +
                "  FOREIGN KEY (item_id) REFERENCES inventory(itemId)" +
                "    ON DELETE CASCADE" +
                ")");

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS user_settings (" +
                "  userId           INTEGER PRIMARY KEY," +
                "  showTotalItems   INTEGER DEFAULT 1," +
                "  showLowStock     INTEGER DEFAULT 1," +
                "  showItemTypes    INTEGER DEFAULT 1," +
                "  showAddedToday   INTEGER DEFAULT 1," +
                "  showLowStockTbl  INTEGER DEFAULT 1," +
                "  showRecentTbl    INTEGER DEFAULT 1," +
                "  showInventoryTbl INTEGER DEFAULT 1," +
                "  showLendingTbl   INTEGER DEFAULT 1," +
                "  showRemovedTbl   INTEGER DEFAULT 1" +
                ")");
            
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS app_config (" +
                "  userId       INTEGER PRIMARY KEY," +
                "  githubToken  TEXT DEFAULT ''," +
                "  githubRepo   TEXT DEFAULT ''," +
                "  vercelBaseUrl TEXT DEFAULT ''," +
                "  geminiKey    TEXT DEFAULT ''," +
                "  claudeKey    TEXT DEFAULT ''" +
            ")");

                // Labs table
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS labs (" +
                    "  labId    INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  labName  TEXT NOT NULL," +
                    "  labSlug  TEXT NOT NULL UNIQUE," +
                    "  userId   INTEGER," +
                    "  FOREIGN KEY (userId) REFERENCES users(userId)" +
            ")");
            //Onedrive
            // Raw sign-out rows pulled from OneDrive
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS signout_records (" +
                "  id          INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  itemName    TEXT NOT NULL," +
                "  quantity    INTEGER NOT NULL DEFAULT 1," +
                "  borrower    TEXT NOT NULL," +
                "  studentId   TEXT DEFAULT ''," +
                "  signoutDate TEXT NOT NULL," +
                "  userId      INTEGER" +
                ")");

            // Raw sign-in rows pulled from OneDrive
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS signin_records (" +
                "  id          INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  itemName    TEXT NOT NULL," +
                "  quantity    INTEGER NOT NULL DEFAULT 1," +
                "  borrower    TEXT NOT NULL," +
                "  signinDate  TEXT NOT NULL," +
                "  userId      INTEGER" +
                ")");

            // Finalized interactions — staff closes them here
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS finalized_lending (" +
                "  id              INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  itemName        TEXT NOT NULL," +
                "  borrower        TEXT NOT NULL," +
                "  studentId       TEXT DEFAULT ''," +
                "  quantityOut     INTEGER NOT NULL," +
                "  quantityReturned INTEGER DEFAULT 0," +
                "  quantityLost    INTEGER DEFAULT 0," +
                "  signoutDate     TEXT," +
                "  finalizedDate   TEXT DEFAULT (date('now'))," +
                "  finalizationReason TEXT," +
                "  userId          INTEGER" +
                ")");
            // Removed items audit table
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS removed_items (" +
                "  id          INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  itemName    TEXT NOT NULL," +
                "  itemType    TEXT DEFAULT ''," +
                "  department  TEXT DEFAULT ''," +
                "  quantity    INTEGER DEFAULT 0," +
                "  unit        TEXT DEFAULT 'pieces'," +
                "  description TEXT DEFAULT ''," +
                "  removedBy   TEXT NOT NULL," +  // username of admin who removed it
                "  removedAt   DATETIME DEFAULT (datetime('now'))," +
                "  reason      TEXT DEFAULT 'Manual removal'," +
                "  userId      INTEGER" +
                ")");
            // Ensuring a shared app-wide config row exists (userId = 0 is reserved)
            st.executeUpdate(
        "INSERT OR IGNORE INTO app_config (userId) VALUES (0)");
            
            
            String[] newCols = {
                "ALTER TABLE app_config ADD COLUMN msClientId     TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msClientSecret TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msTenantId     TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msDriveFileId  TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msRefreshToken TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN ngrokUrl       TEXT DEFAULT ''"
            };
            String[] msColumns = {
                "ALTER TABLE app_config ADD COLUMN msClientId       TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msClientSecret   TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msTenantId       TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msFileOwnerEmail TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msSignOutFileId  TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msSignInFileId   TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN msSignOutSheet   TEXT DEFAULT 'Sheet1'",
                "ALTER TABLE app_config ADD COLUMN msSignInSheet    TEXT DEFAULT 'Sheet1'",
                "ALTER TABLE app_config ADD COLUMN msRefreshToken   TEXT DEFAULT ''",
                "ALTER TABLE app_config ADD COLUMN ngrokUrl         TEXT DEFAULT ''"
            };
            
            String[] inventoryCols = {
                "ALTER TABLE inventory ADD COLUMN description  TEXT DEFAULT ''",
                "ALTER TABLE inventory ADD COLUMN department   TEXT DEFAULT ''",
                "ALTER TABLE inventory ADD COLUMN quantityUnit TEXT DEFAULT 'pieces'",
                "ALTER TABLE inventory ADD COLUMN abbreviation TEXT DEFAULT ''",
                "ALTER TABLE inventory ADD COLUMN remarks      TEXT DEFAULT ''"
            };
            
            for (String col : inventoryCols) {
                try { st.executeUpdate(col); } catch (SQLException ignored) {}
            }
            for (String col : newCols) {
                try { st.executeUpdate(col); } catch (SQLException ignored) {}
            }
            for (String col : msColumns) {
                try { st.executeUpdate(col); } catch (SQLException ignored) {}
            }
            st.executeUpdate(
                "UPDATE app_config SET " +
                "  msClientId       = COALESCE(NULLIF((SELECT msClientId FROM app_config WHERE userId != 0 AND msClientId != '' LIMIT 1), ''), msClientId), " +
                "  msClientSecret   = COALESCE(NULLIF((SELECT msClientSecret FROM app_config WHERE userId != 0 AND msClientSecret != '' LIMIT 1), ''), msClientSecret), " +
                "  msTenantId       = COALESCE(NULLIF((SELECT msTenantId FROM app_config WHERE userId != 0 AND msTenantId != '' LIMIT 1), ''), msTenantId), " +
                "  msFileOwnerEmail = COALESCE(NULLIF((SELECT msFileOwnerEmail FROM app_config WHERE userId != 0 AND msFileOwnerEmail != '' LIMIT 1), ''), msFileOwnerEmail), " +
                "  msSignOutFileId  = COALESCE(NULLIF((SELECT msSignOutFileId FROM app_config WHERE userId != 0 AND msSignOutFileId != '' LIMIT 1), ''), msSignOutFileId), " +
                "  msSignInFileId   = COALESCE(NULLIF((SELECT msSignInFileId FROM app_config WHERE userId != 0 AND msSignInFileId != '' LIMIT 1), ''), msSignInFileId), " +
                "  msSignOutSheet   = COALESCE(NULLIF((SELECT msSignOutSheet FROM app_config WHERE userId != 0 AND msSignOutSheet != '' LIMIT 1), ''), msSignOutSheet), " +
                "  msSignInSheet    = COALESCE(NULLIF((SELECT msSignInSheet FROM app_config WHERE userId != 0 AND msSignInSheet != '' LIMIT 1), ''), msSignInSheet), " +
                "  msRefreshToken   = COALESCE(NULLIF((SELECT msRefreshToken FROM app_config WHERE userId != 0 AND msRefreshToken != '' LIMIT 1), ''), msRefreshToken) " +
                "WHERE userId = 0");
            try {
                st.executeUpdate(
                    "ALTER TABLE users ADD COLUMN department TEXT DEFAULT 'General'"); 
                st.executeUpdate(
        "ALTER TABLE signout_records ADD COLUMN finalized INTEGER DEFAULT 0");
                st.executeUpdate("ALTER TABLE signin_records ADD COLUMN studentId TEXT;");
            } 
            
            catch (SQLException ignored) {}
            System.out.println("Database initialised at: " + DB_PATH);

        } catch (SQLException e) {
            System.err.println("Database init failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite driver not found", e);
        }
        return DriverManager.getConnection(URL);
    }
}