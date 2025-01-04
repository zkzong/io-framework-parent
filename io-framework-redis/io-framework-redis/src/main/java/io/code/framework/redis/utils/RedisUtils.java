package io.code.framework.redis.utils;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static io.code.framework.redis.constant.StringConstant.REDIS_SPLIT_LINE;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> cacheRedis;

    @Resource
    private RedissonClient redissonClient;

    @Value("${redis.prefix:DEFAULT_}")
    private String envPrefix;

    private String assemblyKey(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(envPrefix).append("::");
        if (key.contains(REDIS_SPLIT_LINE)) {
            String[] split = key.split(REDIS_SPLIT_LINE);
            sb.append(split[0]).append("}");
            if (split.length > 1) {
                for (int i = 1; i < split.length; i++) {
                    String s = split[i];
                    if (StringUtils.isNotBlank(s)) {
                        sb.append("_").append(s);
                    }
                }
            }
        } else {
            sb.append(key).append("}");
        }
        return sb.toString();
    }

    public RLock getLock(String key) {
        return redissonClient.getLock(assemblyKey(key));
    }

    public RLock lock(String key, long time, TimeUnit timeUnit) {
        RLock lock = getLock(assemblyKey(key));
        lock.lock(time, timeUnit);
        return lock;
    }

    public boolean hasKey(String key) {
        Boolean hasKey = cacheRedis.hasKey(key);
        return hasKey != null && hasKey;
    }

    public Boolean hasAssemblyKey(String key) {
        return cacheRedis.hasKey(assemblyKey(key));
    }

    public void listRightPush(String key, Object value) {
        cacheRedis.opsForList().rightPush(assemblyKey(key), value);
    }

    public void zSetAdd(String key, String value, double score) {
        cacheRedis.opsForZSet().add(assemblyKey(key), value, score);
    }

    public Set<Object> zSetRangeByScore(String key, double v1, double v2) {
        return cacheRedis.opsForZSet().rangeByScore(assemblyKey(key), v1, v2);
    }

    public void zSetRemoveByRange(String key, Long v1, Long v2) {
        cacheRedis.opsForZSet().removeRange(assemblyKey(key), v1, v2);
    }

    public Long listSize(String key) {
        return cacheRedis.opsForList().size(assemblyKey(key));
    }

    public List<Object> listRange(String key, Long start, Long end) {
        return cacheRedis.opsForList().range(assemblyKey(key), start, end);
    }

    public void listTrim(String key, Long start, Long end) {
        cacheRedis.opsForList().trim(assemblyKey(key), start, end);
    }

    public Object get(String key) {
        return cacheRedis.opsForValue().get(key);
    }

    public void set(String key, String value, int expires) {
        if (expires <= 0) {
            cacheRedis.opsForValue().set(key, value);
        } else {
            cacheRedis.opsForValue().set(key, value, expires, TimeUnit.SECONDS);
        }
    }

    public RLock getFairLock(String key) {
        return redissonClient.getFairLock(assemblyKey(key));
    }

    public RReadWriteLock getReadWriteLock(String key) {
        return redissonClient.getReadWriteLock(assemblyKey(key));
    }
}
