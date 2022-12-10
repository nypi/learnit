import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Translate {

    private static final String TRANSLATE_URL = "http://51.250.18.87:5000/translate";

    public static void main(String[] args) throws IOException {
        //  test
        Translate translate = new Translate();
        System.out.println(translate.translate("Hello, Java!", "en", "ru"));
    }

    // returns json
    public String translate(String text, String langFrom, String langTo) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", text);
        parameters.put("source", langFrom);
        parameters.put("target", langTo);
        parameters.put("format", "text");
        String payload = urlEncode(parameters);

        URL url = new URL(TRANSLATE_URL);
        URLConnection connection = url.openConnection();
        HttpURLConnection http = (HttpURLConnection) connection;
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setFixedLengthStreamingMode(payload.length());
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        http.connect();
        try {
            try (OutputStream out = http.getOutputStream()) {
                out.write(payload.getBytes(StandardCharsets.UTF_8));
            }
            try (BufferedReader r = new BufferedReader(new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8))) {
                String response = r.lines().collect(Collectors.joining("\n"));
                return decodeUnicodeEscaped(response);
            }
        } finally {
            http.disconnect();
        }
    }

    private static String urlEncode(Map<String, String> parameters) {
        return parameters.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    private static String decodeUnicodeEscaped(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int n = str.length();
        while (i < str.length()) {
            if (i < n - 5 && str.charAt(i) == '\\' && str.charAt(i + 1) == 'u') {
                String hex = str.substring(i + 2, i + 6);
                sb.append((char) Integer.parseInt(hex, 16));
                i += 6;
            } else {
                sb.append(str.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }

}

