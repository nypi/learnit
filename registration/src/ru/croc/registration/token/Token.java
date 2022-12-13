package registration.token;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Token {
    /**
     * The method generates a JWT token based on the user id and time
     * @param id ID user's that do authorization
     * @return
     */
    public static String createToken(String id) {
        long time = 1;

        JSONObject jwtPayload = new JSONObject();
        JSONArray audArray = new JSONArray();

        jwtPayload.put("sub", id);
        jwtPayload.put("aud", audArray);
        LocalDateTime ldt = LocalDateTime.now().plusHours(time);
        jwtPayload.put("exp", ldt.toEpochSecond(ZoneOffset.UTC));
        String token = new JWebToken(jwtPayload).toString();
        writeToken(token);
        return token;
    }

    /**
     * The method checks for the existence of the file in which the token is stored
     */
    public static void checkFileToken() {
        if (!Files.exists(Path.of("token.txt")))  Token.createToken("-1");
    }

    /**
     * the method reads the token from the file
     * @return token
     * @throws IOException
     */
    public static String readToken() throws IOException {
        return Files.readString(Paths.get("token.txt"), StandardCharsets.UTF_8);
    }

    /**
     * the method writes the token to a file
     * @param token
     */
    public static void writeToken(String token) {
        try (FileWriter writer = new FileWriter("token.txt", false)) {
            writer.write(token);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
