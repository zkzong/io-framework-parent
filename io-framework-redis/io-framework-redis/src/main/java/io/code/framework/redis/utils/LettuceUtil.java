package io.code.framework.redis.utils;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static io.code.framework.redis.constant.StringConstant.REDIS_SPLIT_LINE;

@Component
public class LettuceUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.prefix:DEFAULT_}")
    private String envPrefix;

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
