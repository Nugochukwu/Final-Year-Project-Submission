package inventorymanagementsystemv_01;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GraphAuthManager {

    private static final String SCOPE =
        "https://graph.microsoft.com/Files.ReadWrite " +
        "https://graph.microsoft.com/User.Read " +
        "offline_access";
    public static Map<String, String> startDeviceCodeFlow()
            throws Exception {

        String body = buildForm(
            "client_id", MicrosoftGraphConfig.clientId,
            "scope",     SCOPE
        );

        String url = "https://login.microsoftonline.com/" +
                MicrosoftGraphConfig.tenantId +
                "/oauth2/v2.0/devicecode";

        String response = post(url, body, null);
        System.out.println("Device code response: " + response);

        Map<String, String> result = parseJson(response);
        if (!result.containsKey("device_code")) {
            throw new Exception("Device code flow failed: " + response);
        }
        return result;
    }
    //  Step 2 — Poll until user completes login in browser
public static boolean pollForToken(String deviceCode, int userId)
        throws Exception {

    // Public client — NO client_secret
    String body = buildForm(
        "grant_type",  "urn:ietf:params:oauth:grant-type:device_code",
        "client_id",   MicrosoftGraphConfig.clientId,
        "device_code", deviceCode
    );

    String url = "https://login.microsoftonline.com/" +
            MicrosoftGraphConfig.tenantId +
            "/oauth2/v2.0/token";

    long deadline = System.currentTimeMillis() + 5 * 60 * 1000;

    while (System.currentTimeMillis() < deadline) {
        Thread.sleep(5000);

        String response = post(url, body, null);
        Map<String, String> json = parseJson(response);

        if (json.containsKey("access_token")) {
            MicrosoftGraphConfig.accessToken =
                    json.get("access_token");
            String rt = json.getOrDefault("refresh_token", "");
            if (!rt.isBlank()) {
                MicrosoftGraphConfig.saveRefreshToken(userId, rt);
            }
            System.out.println("Authentication successful.");
            return true;
        }

        String error = json.getOrDefault("error", "");
        System.out.println("Polling... error=" + error);

        if (error.equals("authorization_pending") ||
                error.equals("slow_down")) {
            continue;
        }

        System.err.println("Auth error: " +
                json.getOrDefault("error_description", error));
        return false;
    }

    System.err.println("Auth timed out.");
    return false;
}

public static boolean refreshAccessToken(int userId) {
    if (MicrosoftGraphConfig.refreshToken.isBlank()) {
        System.err.println("No refresh token — " +
                "re-authentication required.");
        return false;
    }

    try {
        // Public client — NO client_secret
        String body = buildForm(
            "grant_type",    "refresh_token",
            "client_id",     MicrosoftGraphConfig.clientId,
            "refresh_token", MicrosoftGraphConfig.refreshToken,
            "scope",         SCOPE
        );

        String url = "https://login.microsoftonline.com/" +
                MicrosoftGraphConfig.tenantId +
                "/oauth2/v2.0/token";

        String response = post(url, body, null);
        Map<String, String> json = parseJson(response);

        if (json.containsKey("access_token")) {
            MicrosoftGraphConfig.accessToken =
                    json.get("access_token");
            String newRt = json.getOrDefault(
                    "refresh_token",
                    MicrosoftGraphConfig.refreshToken);
            MicrosoftGraphConfig.saveRefreshToken(userId, newRt);
            System.out.println("Token refreshed successfully.");
            return true;
        }

        System.err.println("Refresh failed: " + response);
        return false;

    } catch (Exception e) {
        System.err.println("Token refresh exception: "
                + e.getMessage());
        return false;
    }
}
    //  HTTP POST helper
    private static String post(String url, String body,
                                String bearerToken) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder builder = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type",
                    "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(body));

        if (bearerToken != null && !bearerToken.isBlank()) {
            builder.header("Authorization", "Bearer " + bearerToken);
        }

        HttpResponse<String> response = client.send(
                builder.build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("POST " + url + " → " +
                response.statusCode());
        return response.body();
    }
    private static String buildForm(String... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0)
            throw new IllegalArgumentException(
                    "Must provide even number of key-value pairs");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            if (sb.length() > 0) sb.append('&');
            sb.append(encode(keyValuePairs[i]));
            sb.append('=');
            sb.append(encode(keyValuePairs[i + 1]));
        }
        return sb.toString();
    }

    private static String encode(String value) {
        if (value == null) return "";
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
    public static Map<String, String> parseJson(String json) {
    Map<String, String> map = new LinkedHashMap<>();
    if (json == null || json.isBlank()) return map;

    int i = 0;
    int len = json.length();

    while (i < len) {
        // Find next key (starts with ")
        int keyStart = json.indexOf('"', i);
        if (keyStart < 0) break;
        int keyEnd = findClosingQuote(json, keyStart + 1);
        if (keyEnd < 0) break;

        String key = json.substring(keyStart + 1, keyEnd);
        i = keyEnd + 1;

        // Find the colon
        int colon = json.indexOf(':', i);
        if (colon < 0) break;
        i = colon + 1;

        // Skip whitespace
        while (i < len && Character.isWhitespace(json.charAt(i))) i++;
        if (i >= len) break;

        char next = json.charAt(i);

        if (next == '"') {
            // String value
            int valEnd = findClosingQuote(json, i + 1);
            if (valEnd < 0) break;
            String val = json.substring(i + 1, valEnd)
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\")
                    .replace("\\n", "\n")
                    .replace("\\r", "")
                    .replace("\\t", "\t");
            map.put(key, val);
            i = valEnd + 1;
        } else if (next == '-' || Character.isDigit(next)) {
            // Number value
            int numEnd = i;
            while (numEnd < len) {
                char c = json.charAt(numEnd);
                if (c == ',' || c == '}' || c == ']'
                        || Character.isWhitespace(c)) break;
                numEnd++;
            }
            map.put(key, json.substring(i, numEnd).trim());
            i = numEnd;
        } else if (next == 't' || next == 'f') {
            // Boolean true/false
            if (i + 4 <= len &&
                    json.substring(i, i + 4).equals("true")) {
                map.put(key, "true");
                i += 4;
            } else if (i + 5 <= len &&
                    json.substring(i, i + 5).equals("false")) {
                map.put(key, "false");
                i += 5;
            } else {
                i++;
            }
        } else if (next == 'n') {
            // null
            map.put(key, "");
            i += 4;
        } else if (next == '{' || next == '[') {
            // Nested object/array — skip over it
            i = skipNested(json, i);
        } else {
            i++;
        }
    }

    return map;
}

// Find the closing quote of a JSON string,
// correctly handling backslash escapes
private static int findClosingQuote(String json, int start) {
    int i = start;
    while (i < json.length()) {
        char c = json.charAt(i);
        if (c == '\\') {
            i += 2; // skip escaped character
        } else if (c == '"') {
            return i;
        } else {
            i++;
        }
    }
    return -1;
}

// Skip over a nested JSON object {} or array []
private static int skipNested(String json, int start) {
    char open  = json.charAt(start);
    char close = open == '{' ? '}' : ']';
    int depth  = 0;
    int i      = start;
    boolean inString = false;

    while (i < json.length()) {
        char c = json.charAt(i);
        if (inString) {
            if (c == '\\') i++; // skip escape
            else if (c == '"') inString = false;
        } else {
            if (c == '"') inString = true;
            else if (c == open)  depth++;
            else if (c == close) {
                depth--;
                if (depth == 0) return i + 1;
            }
        }
        i++;
    }
    return i;
}
}