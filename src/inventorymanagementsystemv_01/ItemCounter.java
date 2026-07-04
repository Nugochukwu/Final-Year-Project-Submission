package inventorymanagementsystemv_01;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemCounter {

    private static boolean opencvLoaded = false;

    public static boolean loadOpenCV() {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            opencvLoaded = true;
            return true;
        } catch (UnsatisfiedLinkError e) {
            System.err.println("OpenCV failed to load: " + e.getMessage());
            return false;
        }
    }

    public static List<CameraDevice> getAvailableCameras() {
        List<CameraDevice> devices = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            VideoCapture cam = new VideoCapture(i);
            if (cam.isOpened()) {
                String label = i == 0 ? "Built-in Camera" : "External Camera " + i;
                devices.add(new CameraDevice(i, label, null));
                cam.release();
            }
        }
        return devices;
    }
    //--------------------
    //--------------------
    
    public static CountResult countWithClaude(java.awt.image.BufferedImage image,
                                                String apiKey) {
         try {
             java.io.File temp = java.io.File.createTempFile("claude_count", ".jpg");
             temp.deleteOnExit();
             javax.imageio.ImageIO.write(image, "jpg", temp);

             byte[] imageBytes = java.nio.file.Files.readAllBytes(temp.toPath());
             String base64 = java.util.Base64.getEncoder()
                     .encodeToString(imageBytes);

             String requestBody = "{" +
                 "\"model\":\"claude-opus-4-5\"," +
                 "\"max_tokens\":50," +
                 "\"messages\":[{\"role\":\"user\",\"content\":[" +
                 "{\"type\":\"image\",\"source\":{" +
                 "  \"type\":\"base64\"," +
                 "  \"media_type\":\"image/jpeg\"," +
                 "  \"data\":\"" + base64 + "\"}}," +
                 "{\"type\":\"text\",\"text\":\"Count the individual items " +
                 "in this image. Reply with a single integer only.\"}" +
                 "]}]}";

             java.net.http.HttpClient client =
                     java.net.http.HttpClient.newHttpClient();
             java.net.http.HttpRequest request =
                     java.net.http.HttpRequest.newBuilder()
                 .uri(java.net.URI.create("https://api.anthropic.com/v1/messages"))
                 .header("Content-Type", "application/json")
                 .header("x-api-key", apiKey)
                 .header("anthropic-version", "2023-06-01")
                 .POST(java.net.http.HttpRequest.BodyPublishers
                         .ofString(requestBody))
                 .build();

             java.net.http.HttpResponse<String> response = client.send(
                     request, java.net.http.HttpResponse.BodyHandlers.ofString());

             String body = response.body();
             int start = body.indexOf("\"text\":\"") + 8;
             int end   = body.indexOf("\"", start);
             String count = body.substring(start, end)
                     .replaceAll("[^0-9]", "").trim();

             return count.isEmpty()
                 ? new CountResult(-1, "No number returned")
                 : new CountResult(Integer.parseInt(count), "Success");

         } catch (Exception e) {
             return new CountResult(-1, "Claude error: " + e.getMessage());
         }
     }
    
    //--------------------
    //--------------------
    public static CountResult countWithGemini(java.io.File imageFile,
                                               String apiKey) {
        try {
            // Convert image to base64
            byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());
            String base64Image = java.util.Base64.getEncoder()
                    .encodeToString(imageBytes);

            // Detect mime type
            String mimeType = imageFile.getName().toLowerCase().endsWith(".png")
                    ? "image/png" : "image/jpeg";

            // Build request JSON
            String requestBody = "{"
                + "\"contents\":[{"
                + "  \"parts\":["
                + "    {\"inline_data\":{\"mime_type\":\"" + mimeType + "\","
                + "     \"data\":\"" + base64Image + "\"}},"
                + "    {\"text\":\"Count the number of individual items in this image. "
                + "     The items are small components such as screws, resistors, or bolts "
                + "     on a plain background. Reply with a single integer only. "
                + "     Do not include any other text.\"}"
                + "  ]"
                + "}]}";

            // Send request to Gemini
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(
                    "https://generativelanguage.googleapis.com/v1beta/models/" +
                    "gemini-2.5-flash:generateContent?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            java.net.http.HttpResponse<String> response = client.send(
                    request, java.net.http.HttpResponse.BodyHandlers.ofString());

            // Parse response
            String body = response.body();

            // Extract the text from the JSON response
            int textStart = body.indexOf("\"text\": \"") + 9;
            int textEnd   = body.indexOf("\"", textStart);
            if (textStart < 9 || textEnd < 0) {
                System.err.println("Gemini response: " + body);
                return new CountResult(-1, "Could not parse Gemini response");
            }

            String countText = body.substring(textStart, textEnd).trim();

            // Strip any non-numeric characters just in case
            countText = countText.replaceAll("[^0-9]", "");

            if (countText.isEmpty())
                return new CountResult(-1, "Gemini returned no number");

            return new CountResult(Integer.parseInt(countText), "Success");

        } catch (Exception e) {
            System.err.println("Gemini count failed: " + e.getMessage());
            return new CountResult(-1, "Gemini error: " + e.getMessage());
        }
    }

    // Overload for camera capture — takes a BufferedImage directly
    public static CountResult countWithGemini(java.awt.image.BufferedImage image,
                                               String apiKey) {
        try {
            // Convert BufferedImage to temp file then call file version
            java.io.File temp = java.io.File.createTempFile("gemini_count", ".jpg");
            temp.deleteOnExit();
            javax.imageio.ImageIO.write(image, "jpg", temp);
            return countWithGemini(temp, apiKey);
        } catch (Exception e) {
            return new CountResult(-1, "Image conversion failed: " + e.getMessage());
        }
    }
    //--------------------
    //--------------------
    //--------------------
    //--------------------
    public static CountResult countFromFile(File imageFile, int minItemArea) {
        if (!opencvLoaded) return new CountResult(-1, "OpenCV not loaded");
        Mat image = Imgcodecs.imread(imageFile.getAbsolutePath());
        if (image.empty()) return new CountResult(-1, "Could not read image");
        return processImage(image, minItemArea);
    }

    public static CountResult countFromCamera(int cameraIndex, int minItemArea) {
    if (!opencvLoaded) return new CountResult(-1, "OpenCV not loaded");
    
    VideoCapture camera = new VideoCapture(cameraIndex);
    if (!camera.isOpened())
        return new CountResult(-1, "Camera " + cameraIndex + " not available");

    // Set buffer size to 1 so we always get the latest frame
    camera.set(org.opencv.videoio.Videoio.CAP_PROP_BUFFERSIZE, 1);

    Mat frame = new Mat();
    
    // Warmup — grab and discard frames until we get a real one
    for (int i = 0; i < 30; i++) {
        camera.grab();
        try { Thread.sleep(50); } catch (InterruptedException e) {}
    }
    camera.retrieve(frame); // retrieve the last grabbed frame
    camera.release();

    if (frame.empty()) return new CountResult(-1, "Could not capture frame");
    return processImage(frame, minItemArea);
}

// Same fix for previewFromCamera
public static BufferedImage previewFromCamera(int cameraIndex) {
    if (!opencvLoaded) return null;
    
    VideoCapture camera = new VideoCapture(cameraIndex);
    if (!camera.isOpened()) return null;

    camera.set(org.opencv.videoio.Videoio.CAP_PROP_BUFFERSIZE, 1);

    Mat frame = new Mat();
    for (int i = 0; i < 30; i++) {
        camera.grab();
        try { Thread.sleep(50); } catch (InterruptedException e) {}
    }
    camera.retrieve(frame);
    camera.release();

    return frame.empty() ? null : matToBufferedImage(frame);
}

    public static CountResult countFromIPStream(String streamUrl, int minItemArea) {
        if (!opencvLoaded) return new CountResult(-1, "OpenCV not loaded");
        VideoCapture camera = new VideoCapture(streamUrl);
        if (!camera.isOpened())
            return new CountResult(-1, "Could not connect to stream.");
        Mat frame = new Mat();
        camera.read(frame);
        camera.release();
        if (frame.empty()) return new CountResult(-1, "No frame received");
        return processImage(frame, minItemArea);
    }

    

    public static BufferedImage previewFromIPStream(String streamUrl) {
        if (!opencvLoaded) return null;
        VideoCapture camera = new VideoCapture(streamUrl);
        if (!camera.isOpened()) return null;
        Mat frame = new Mat();
        camera.read(frame);
        camera.release();
        return frame.empty() ? null : matToBufferedImage(frame);
    }

    private static CountResult processImage(Mat image, int minItemArea) {
        Mat grey  = new Mat();
        Mat blur  = new Mat();
        Mat thresh = new Mat();
        Imgproc.cvtColor(image, grey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(grey, blur, new Size(11, 11), 0);
        Imgproc.threshold(blur, thresh, 0, 255,
                Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
        Mat kernel = Imgproc.getStructuringElement(
                Imgproc.MORPH_ELLIPSE, new Size(9, 9));
        Imgproc.morphologyEx(thresh, thresh, Imgproc.MORPH_CLOSE, kernel);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(thresh, contours, new Mat(),
                Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        long count = contours.stream()
                .filter(c -> Imgproc.contourArea(c) > minItemArea)
                .count();
        return new CountResult((int) count, "Success");
    }

    public static BufferedImage matToBufferedImage(Mat mat) {
        int type = mat.channels() > 1
                ? BufferedImage.TYPE_3BYTE_BGR
                : BufferedImage.TYPE_BYTE_GRAY;
        byte[] buffer = new byte[mat.channels() * mat.cols() * mat.rows()];
        mat.get(0, 0, buffer);
        BufferedImage img = new BufferedImage(mat.cols(), mat.rows(), type);
        img.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), buffer);
        return img;
    }

    public static class CameraDevice {
        public final int    index;
        public final String label;
        public final String streamUrl;
        public CameraDevice(int index, String label, String streamUrl) {
            this.index = index; this.label = label; this.streamUrl = streamUrl;
        }
        public boolean isIPCamera() { return streamUrl != null; }
        @Override public String toString() { return label; }
    }

    public static class CountResult {
        public final int    count;
        public final String message;
        public CountResult(int count, String message) {
            this.count = count; this.message = message;
        }
        public boolean isSuccess() { return count >= 0; }
    }
}
