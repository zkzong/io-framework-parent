package io.code.framework.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: zongz
 * @Date: 2025-01-04
 */
@Configuration
public class LettuceConfig {

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * 连接工厂：RedisConnectionFactory LettuceConnectionFactory
     */
    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);

        // 使用 StringRedisSerializer 作为 key 和 value 的序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        //GenericToStringSerializer genericToStringSerializer = new GenericToStringSerializer(Object.class);
        //template.setValueSerializer(genericToStringSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

}
