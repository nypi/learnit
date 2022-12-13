package registration.token;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * The class required to generate the token
 */
public class JWebToken {
    private static final String SECRET_KEY = "FREE_MASON";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static final String ISSUER = "mason.metamug.net";
    private static final String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    private JSONObject payload;
    private String signature;
    private String encodedHeader;

    private JWebToken() {
        this.payload = new JSONObject();
        this.encodedHeader = encode(new JSONObject("{\"alg\":\"HS256\",\"typ\":\"JWT\"}"));
    }

    public JWebToken(JSONObject payload) {
        this(payload.getString("sub"), payload.getJSONArray("aud"), payload.getLong("exp"));
    }

    public JWebToken(String sub, JSONArray aud, long expires) {
        this();
        this.payload.put("sub", sub);
        this.payload.put("aud", aud);
        this.payload.put("exp", expires);
        this.payload.put("iat", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        this.payload.put("iss", "mason.metamug.net");
        this.payload.put("jti", UUID.randomUUID().toString());
        this.signature = this.hmacSha256(this.encodedHeader + "." + encode(this.payload), "FREE_MASON");
    }

    public JWebToken(String token) throws NoSuchAlgorithmException {
        this();
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid Token format");
        } else if (this.encodedHeader.equals(parts[0])) {
            this.encodedHeader = parts[0];
            this.payload = new JSONObject(decode(parts[1]));
            if (this.payload.isEmpty()) {
                throw new JSONException("Payload is Empty: ");
            } else if (!this.payload.has("exp")) {
                throw new JSONException("Payload doesn't contain expiry " + this.payload);
            } else {
                this.signature = parts[2];
            }
        } else {
            throw new NoSuchAlgorithmException("JWT Header is Incorrect: " + parts[0]);
        }
    }

    public String toString() {
        return this.encodedHeader + "." + encode(this.payload) + "." + this.signature;
    }

    public boolean isValid() {
        return this.payload.getLong("exp") > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) && this.signature.equals(this.hmacSha256(this.encodedHeader + "." + encode(this.payload), "FREE_MASON"));
    }

    public String getSubject() {
        return this.payload.getString("sub");
    }

    public List<String> getAudience() {
        JSONArray arr = this.payload.getJSONArray("aud");
        List<String> list = new ArrayList();

        for (int i = 0; i < arr.length(); ++i) {
            list.add(arr.getString(i));
        }

        return list;
    }

    private static String encode(JSONObject obj) {
        return encode(obj.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    private String hmacSha256(String data, String secret) {
        try {
            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);
            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return encode(signedBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException var7) {
            return null;
        }
    }
}

