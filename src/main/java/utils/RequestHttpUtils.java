package utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class RequestHttpUtils {
    public static HttpHeaders getBearerTokenHeaders(String credentials) {
        HttpHeaders headers = new HttpHeaders();

        try {
            SSLUtils.turnOffSslChecking();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + credentials);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return headers;
    }
}
