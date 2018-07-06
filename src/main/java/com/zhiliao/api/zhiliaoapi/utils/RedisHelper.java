package com.zhiliao.api.zhiliaoapi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisHelper {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String getString(String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(token);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
