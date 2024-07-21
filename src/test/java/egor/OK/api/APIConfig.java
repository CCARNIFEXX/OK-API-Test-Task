package egor.OK.api;


import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
public class APIConfig {
    private final String accessToken = System.getenv("ACCESS_TOKEN");
    private final String application_secret_key = System.getenv("APPLICATION_SECRET_KEY");
    private final String application_key = System.getenv("APPLICATION_KEY");


    public String getSig(Map<String, String> queryParams) throws NoSuchAlgorithmException {
        String secretKey = md5(accessToken + application_secret_key);
        String collected = queryParams.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(""));

        return md5(collected + secretKey);
    }

    private static String md5(String inputString) throws NoSuchAlgorithmException {

        byte[] bytesMD5 = MessageDigest.getInstance("MD5").digest(inputString.getBytes());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesMD5);
        StringBuilder hexString = new StringBuilder();
        int byteValue;

        while ((byteValue = inputStream.read()) != -1) {
            hexString.append(String.format("%02X", byteValue));
        }

        return hexString.toString();

    }
}
