package inventorymanagementsystemv_01;

import java.net.http.*;
import java.net.URI;
import java.sql.*;
import java.util.Base64;


///
///
///
///
///
///**
///Scrapped Vercel Form generator
///
///
///
///
///
public class FormPublisher {

    private static String getLocalIp() {
    try {
        return java.net.InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
        return "127.0.0.1";
    }
}
    public static boolean publishLabForm(int userId, int labId,
                                          String labName, String labSlug) {
        try {
            String html = buildFormHtml(userId, labId, labName, labSlug);
            String filePath = "labs/" + labSlug + "/index.html";
            return pushToGitHub(filePath, html,
                    "Update form for " + labName);
        } catch (Exception e) {
            System.err.println("Publish failed: " + e.getMessage());
            return false;
        }
    }
    public static void publishAllForms(int userId) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT labId, labName, labSlug FROM labs WHERE userId = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                publishLabForm(userId,
                    rs.getInt("labId"),
                    rs.getString("labName"),
                    rs.getString("labSlug"));
            }
        } catch (SQLException e) {
            System.err.println("Publish all failed: " + e.getMessage());
        }
    }

    private static String buildFormHtml(int userId, int labId,
                                         String labName, String labSlug) {
        // Build item options for this lab's inventory
        StringBuilder options = new StringBuilder();
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT itemId, name, quantity FROM inventory " +
                "WHERE userId = ? AND quantity > 0 ORDER BY name")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                options.append("<option value='")
                       .append(rs.getInt("itemId")).append("'>")
                       .append(rs.getString("name"))
                       .append(" (").append(rs.getInt("quantity"))
                       .append(" available)</option>");
            }
        } catch (SQLException e) {
            options.append("<option>Error loading items</option>");
        }

        // The form POSTs to your Java app's exposed port
        // Replace with your actual public IP or ngrok URL from settings
        

        return "<!DOCTYPE html><html><head>" +
            "<meta charset='UTF-8'>" +
            "<meta name='viewport' content='width=device-width,initial-scale=1'>" +
            "<title>" + labName + " Item Request</title>" +
            "<style>" +
            "*{box-sizing:border-box;margin:0;padding:0}" +
            "body{font-family:'Segoe UI',sans-serif;background:#eef2ff;" +
            "     min-height:100vh;display:flex;justify-content:center;" +
            "     padding:24px 16px}" +
            ".card{background:#fff;border-radius:16px;padding:28px;" +
            "      width:100%;max-width:500px;box-shadow:0 4px 24px" +
            "      rgba(33,55,110,.12);align-self:flex-start}" +
            "header{text-align:center;margin-bottom:24px}" +
            "header h1{color:#21376e;font-size:1.25rem;margin-bottom:4px}" +
            "header p{color:#888;font-size:.85rem}" +
            ".badge{display:inline-block;background:#eef2ff;color:#21376e;" +
            "       border-radius:20px;padding:3px 12px;font-size:.8rem;" +
            "       font-weight:600;margin-bottom:16px}" +
            "label{display:block;font-size:.75rem;font-weight:700;" +
            "      text-transform:uppercase;letter-spacing:.05em;" +
            "      color:#555;margin-bottom:5px}" +
            "select,input,textarea{width:100%;padding:11px 13px;" +
            "  border:1.5px solid #dde3f5;border-radius:8px;" +
            "  font-size:.95rem;margin-bottom:18px;outline:none;" +
            "  transition:border .2s}" +
            "select:focus,input:focus,textarea:focus{border-color:#6799ff}" +
            "button{width:100%;padding:14px;border:none;border-radius:8px;" +
            "  background:#21376e;color:#fff;font-size:1rem;font-weight:700;" +
            "  cursor:pointer;transition:background .2s}" +
            "button:hover{background:#3a5bbf}" +
            ".req{color:#e24b4a}" +
            "</style></head><body><div class='card'>" +
            "<header>" +
            "<h1>Pan-Atlantic University</h1>" +
            "<p>School of Science &amp; Technology</p>" +
            "</header>" +
            "<div style='text-align:center'>" +
            "<span class='badge'>" + labName + "</span></div>" +
            "<form id='form'>" +
            "<input type='hidden' name='labId' value='" + labId + "'>" +
            "<input type='hidden' name='labSlug' value='" + labSlug + "'>" +

            "<label>Full Name <span class='req'>*</span></label>" +
            "<input type='text' name='borrower' placeholder='e.g. John Okafor'" +
            "       required>" +

            "<label>Student ID</label>" +
            "<input type='text' name='studentId' placeholder='e.g. PAU/2024/001'>" +

            "<label>Item Requested <span class='req'>*</span></label>" +
            "<select name='itemId' required>" +
            "<option value=''>-- Select an item --</option>" +
            options +
            "</select>" +

            "<label>Quantity <span class='req'>*</span></label>" +
            "<input type='number' name='quantity' min='1' value='1' required>" +

            "<label>Purpose <span class='req'>*</span></label>" +
            "<select name='reason' required>" +
            "<option value=''>-- Select --</option>" +
            "<option>Lab Practical</option>" +
            "<option>Student Project</option>" +
            "<option>Assignment</option>" +
            "<option>Personal Use</option>" +
            "<option>Other</option>" +
            "</select>" +

            "<label>Additional Notes</label>" +
            "<textarea name='notes' rows='3'" +
            "  placeholder='Any extra details...'></textarea>" +

            "<label>Expected Return Date <span class='req'>*</span></label>" +
            "<input type='date' name='returnDate' required>" +

            "<button type='submit'>Submit Request</button>" +
            "</form>" +

            // JS posts to Java app and shows result inline
            "<script>" +
            "document.getElementById('form').addEventListener('submit',async e=>{" +
            "  e.preventDefault();" +
            "  const btn=e.target.querySelector('button');" +
            "  btn.textContent='Submitting...';" +
            "  btn.disabled=true;" +
            "  try{" +
            "    const res=await fetch('http://\" + getLocalIp() + \":8765/lend',{" +
            "      method:'POST'," +
            "      headers:{'Content-Type':'application/x-www-form-urlencoded'}," +
            "      body:new URLSearchParams(new FormData(e.target))" +
            "    });" +
            "    const txt=await res.text();" +
            "    document.body.innerHTML=txt;" +
            "  }catch(err){" +
            "    btn.textContent='Submit Request';" +
            "    btn.disabled=false;" +
            "    alert('Could not reach the server. Make sure you are on the university WiFi.');" +
            "  }" +
            "});" +
            "</script>" +
            "</div></body></html>";
    }

    // Push a file to GitHub via the API
    private static boolean pushToGitHub(String filePath,
                                         String content,
                                         String commitMessage) {
        try {
            // Print exactly what is being sent so you can verify
        System.out.println("Pushing to repo: [" + GitHubConfig.githubRepo + "]");
        System.out.println("File path: " + filePath);
        System.out.println("Token starts with: " +
            (GitHubConfig.githubToken.length() > 8
                ? GitHubConfig.githubToken.substring(0, 8) + "..."
                : "(empty or too short)"));
            String encodedContent = Base64.getEncoder()
                    .encodeToString(content.getBytes("UTF-8"));

            // First get the current SHA of the file (needed for updates)
            String sha = getFileSha(filePath);

            // Build request body
            String body = "{" +
                "\"message\":\"" + commitMessage + "\"," +
                "\"content\":\"" + encodedContent + "\"" +
                (sha != null ? ",\"sha\":\"" + sha + "\"" : "") +
                "}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/repos/" +
                        GitHubConfig.githubRepo +
                        "/contents/" + filePath))
                .header("Authorization", "Bearer " + GitHubConfig.githubToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/vnd.github+json")
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());

            boolean ok = response.statusCode() == 200
                      || response.statusCode() == 201;
            if (!ok)
                System.err.println("GitHub push failed: " + response.body());
            return ok;

        } catch (Exception e) {
            System.err.println("GitHub push error: " + e.getMessage());
            return false;
        }
    }

    private static String getFileSha(String filePath) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/repos/" +
                        GitHubConfig.githubRepo +
                        "/contents/" + filePath))
                .header("Authorization", "Bearer " + GitHubConfig.githubToken)
                .header("Accept", "application/vnd.github+json")
                .GET()
                .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String body = response.body();
                int shaStart = body.indexOf("\"sha\":\"") + 7;
                int shaEnd   = body.indexOf("\"", shaStart);
                if (shaStart > 6)
                    return body.substring(shaStart, shaEnd);
            }
        } catch (Exception e) {
            System.err.println("SHA fetch failed: " + e.getMessage());
        }
        return null; // file doesn't exist yet — first push
    }
}