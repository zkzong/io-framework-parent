package io.code.framework.redis.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class LettuceUtil extends RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public boolean hasKey(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }

    public Boolean hasAssemblyKey(String key) {
        return redisTemplate.hasKey(assemblyKey(key));
    }

    public void listRightPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(assemblyKey(key), value);
    }

    public void zSetAdd(String key, String value, double score) {
        redisTemplate.opsForZSet().add(assemblyKey(key), value, score);
    }

    public Set<Object> zSetRangeByScore(String key, double v1, double v2) {
        return redisTemplate.opsForZSet().rangeByScore(assemblyKey(key), v1, v2);
    }

    public void zSetRemoveByRange(String key, Long v1, Long v2) {
        redisTemplate.opsForZSet().removeRange(assemblyKey(key), v1, v2);
    }

    public Long listSize(String key) {
        return redisTemplate.opsForList().size(assemblyKey(key));
    }

    public List<Object> listRange(String key, Long start, Long end) {
        return redisTemplate.opsForList().range(assemblyKey(key), start, end);
    }

    public void listTrim(String key, Long start, Long end) {
        redisTemplate.opsForList().trim(assemblyKey(key), start, end);
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
