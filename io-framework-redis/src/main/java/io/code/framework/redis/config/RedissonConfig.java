package io.code.framework.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zongz
 * @Date: 2025-01-04
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private String port;
    @Value("${spring.data.redis.password}")
    private String password;

    private static final String prefix = "redis://";

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        // 1.配置连接
        Config config = new Config();
        config.useSingleServer()
                //可以用"redis://"来启用SSL连接
                .setAddress(prefix + host + ":" + port)
                .setPassword(password);
        // 2.创建客户端
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


}
