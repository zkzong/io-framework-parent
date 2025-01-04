package io.code.framework.redis.utils;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static io.code.framework.redis.constant.StringConstant.REDIS_SPLIT_LINE;

/**
 * @Author: zongz
 * @Date: 2025-01-04
 */
@Component
public class RedissonUtil {

    @Resource
    private RedissonClient redissonClient;

    @Value("${redis.prefix:DEFAULT_}")
    private String envPrefix;

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

}
