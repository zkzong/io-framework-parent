package io.code.framework.redis.utils;

import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zongz
 * @Date: 2025-01-04
 */
@Component
public class RedissonUtil extends RedisUtil {

    @Resource
    private RedissonClient redissonClient;

    public RLock getLock(String key) {
        return redissonClient.getLock(assemblyKey(key));
    }

    public RLock lock(String key, long time, TimeUnit timeUnit) {
        RLock lock = getLock(assemblyKey(key));
        lock.lock(time, timeUnit);
        return lock;
    }

    public RLock getFairLock(String key) {
        return redissonClient.getFairLock(assemblyKey(key));
    }

    public RReadWriteLock getReadWriteLock(String key) {
        return redissonClient.getReadWriteLock(assemblyKey(key));
    }

}
