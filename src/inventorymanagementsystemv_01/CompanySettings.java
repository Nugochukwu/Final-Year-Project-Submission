package inventorymanagementsystemv_01;

public class CompanySettings {

    private static String companyName  = "Pan Atlantic University";
    private static String address      = "Km 52 Lekki - Epe Expy, Lagos 105101, Lagos, Nigeria";
    private static String contactEmail = "";
    private static String contactPhone = "+234 708 864 1465";
    private static String logoPath     = "resources/logo.jpg";

    // Getters
    public static String getCompanyName()  { return companyName; }
    public static String getAddress()      { return address; }
    public static String getContactEmail() { return contactEmail; }
    public static String getContactPhone() { return contactPhone; }
    public static String getLogoPath()     { return logoPath; }

    // Setters
    public static void setCompanyName(String v)  { companyName  = v; }
    public static void setAddress(String v)      { address      = v; }
    public static void setContactEmail(String v) { contactEmail = v; }
    public static void setContactPhone(String v) { contactPhone = v; }
    public static void setLogoPath(String v)     { logoPath     = v; }

    public static boolean hasLogo() {
        return logoPath != null && !logoPath.isBlank();
    }
    public static void saveSettings() {
        java.util.prefs.Preferences prefs =
            java.util.prefs.Preferences.userNodeForPackage(CompanySettings.class);
        prefs.put("companyName",  companyName);
        prefs.put("address",      address);
        prefs.put("contactEmail", contactEmail);
        prefs.put("contactPhone", contactPhone);
        prefs.put("logoPath",     logoPath);
    }

    public static void loadSettings() {
        java.util.prefs.Preferences prefs =
            java.util.prefs.Preferences.userNodeForPackage(CompanySettings.class);
        companyName  = prefs.get("companyName",  companyName);
        address      = prefs.get("address",      address);
        contactEmail = prefs.get("contactEmail", contactEmail);
        contactPhone = prefs.get("contactPhone", contactPhone);
        logoPath     = prefs.get("logoPath",     logoPath);
    }

    
}