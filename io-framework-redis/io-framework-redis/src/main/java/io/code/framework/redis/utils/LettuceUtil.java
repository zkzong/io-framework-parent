package io.code.framework.redis.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class LettuceUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public boolean hasKey(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }

    public Boolean hasAssemblyKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void listRightPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public void zSetAdd(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> zSetRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public void zSetRemoveByRange(String key, Long start, Long end) {
        redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    public Long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public List<Object> listRange(String key, Long start, Long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public void listTrim(String key, Long start, Long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value, int expires) {
        if (expires <= 0) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, expires, TimeUnit.SECONDS);
        }
    }

}
