package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.NonNull;
import org.commons.Key;
import org.commons.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;

class ComlinkApi {
    private final String accessKey;
    private final String secretKey;
    private final String apiUrl;
    private final Logger logger;

    public ComlinkApi(@NonNull final Key accessKey,@NonNull final Key secretKey,@NonNull final Url apiUrl) {
        this.accessKey = accessKey.toString();
        this.secretKey = secretKey.toString();
        this.apiUrl = apiUrl.toString();
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public JsonElement sendRequest(@NonNull final ComlinkRequest comlinkRequest){
        try {
            final JsonElement jsonElement = this.sendRequest(comlinkRequest.getJsonBodyAsString(),
                    comlinkRequest.getMethod(),comlinkRequest.getEndpoint());

            if(jsonElement.getAsJsonObject().has("message")){//when request failed
                return null;
            }
            return jsonElement;
        }catch (Exception exception) {
            logger.error(exception.getMessage());
        }
        return null;
    }

    private JsonElement sendRequest(@NonNull final String jsonBody, @NonNull final String method,
                                    @NonNull final String path) throws Exception {
        String xDate = String.valueOf(Instant.now().toEpochMilli());

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md.digest(jsonBody.getBytes("UTF-8"));
        String md5Hex = HexFormat.of().formatHex(md5Bytes);

        String dataToSign = xDate + method + path + md5Hex;

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256"));
        byte[] hmacBytes = mac.doFinal(dataToSign.getBytes("UTF-8"));
        String hmacHex = HexFormat.of().formatHex(hmacBytes);

        String authorization = "HMAC-SHA256 Credential=" + accessKey + ",Signature=" + hmacHex;
        URL url = new URL(apiUrl+path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-Date", xDate);
        conn.setRequestProperty("Authorization", authorization);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (responseCode < 200 || responseCode >= 300) {
            throw new RuntimeException("Comlink API error: " + baos.toString(StandardCharsets.UTF_8));
        }
        byte[] buffer = new byte[1024];
        int read;
        while ((read = is.read(buffer)) != -1) {
            baos.write(buffer, 0, read);
        }

        return toJsonElement(baos.toString(StandardCharsets.UTF_8));
    }

    private static JsonElement toJsonElement(String jsonString) throws Exception{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(jsonString, JsonElement.class);
    }
}
