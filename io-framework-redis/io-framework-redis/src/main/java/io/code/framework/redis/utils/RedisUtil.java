package io.code.framework.redis.utils;

import org.springframework.beans.factory.annotation.Value;

import static io.code.framework.redis.constant.StringConstant.COLON;

/**
 * @Author: zongz
 * @Date: 2025-01-04
 */
public class RedisUtil {

    @Value("${redis.prefix:prefix}")
    private String envPrefix;

    protected String assemblyKey(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(envPrefix).append(COLON).append(key);
        return sb.toString();
    }

}
