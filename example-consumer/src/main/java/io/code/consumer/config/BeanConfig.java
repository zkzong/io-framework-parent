package io.code.consumer.config;

import io.code.framework.core.trace.interceptor.RestTemplateTraceIdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @Author: zongz
 * @Date: 2024/11/16
 */
@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Arrays.asList(new RestTemplateTraceIdInterceptor()));
        return restTemplate;
    }

}
