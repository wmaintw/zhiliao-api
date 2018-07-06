package com.zhiliao.api.zhiliaoapi.utils;

import com.zhiliao.api.zhiliaoapi.exceptions.ServerErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.HOURS;

@Component
public class SecurityHelper {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${app.auth.token.timeout}")
    private long tokenTimeout;

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

    public static String extractToken(String bearerToken) {
        return bearerToken.substring("Bearer ".length());
    }

    public String saveLoginStatus(String username, String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(token, username, tokenTimeout, HOURS);
        return token;
    }

    public boolean isAuthenticated(String username, String providedToken) {
        if (!redisTemplate.hasKey(username)) {
            return false;
        }

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String tokenInRedis = ops.get(username);
        if (StringUtils.isBlank(tokenInRedis)) {
            return false;
        }

        if (tokenInRedis.equals(providedToken)) {
            refreshToken(username, tokenInRedis);
            return true;
        }

        return false;
    }

    private void refreshToken(String username, String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(username, token, tokenTimeout, HOURS);
    }
}
