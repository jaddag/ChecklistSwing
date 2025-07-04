package translator;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TranslatorBeta {

    private static TranslatorBeta instance;

    public static TranslatorBeta getInstance() {
        if (instance == null) {
            instance = new TranslatorBeta();
        }
        return instance;
    }

    public String translate(String text, String sourceLang, String targetLang) throws IOException {
        String apiUrl = "https://translate.argosopentech.com/translate";

        String jsonInput = String.format(
                "{\"q\":\"%s\",\"source\":\"%s\",\"target\":\"%s\",\"format\":\"text\"}",
                text, sourceLang, targetLang
        );

        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            InputStream error = conn.getErrorStream();
            String errText = new String(error.readAllBytes(), StandardCharsets.UTF_8);
            throw new IOException("HTTP error: " + conn.getResponseCode() + "\n" + errText);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }
        }

        String json = response.toString();
        int start = json.indexOf(":\"") + 2;
        int end = json.lastIndexOf("\"");
        return json.substring(start, end);
    }


}

