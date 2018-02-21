package com.zhiliao.api.zhiliaoapi.utils;

import com.zhiliao.api.zhiliaoapi.exceptions.ServerErrorException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static java.lang.String.format;

@Component
public class SecurityHelper {
    private static final String ALGORITHM_SHA_256 = "SHA-256";

    public static String hash(String text) {
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance(ALGORITHM_SHA_256);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }

        byte[] encryptedMessage = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        return format("%064x", new java.math.BigInteger(1, encryptedMessage));
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
