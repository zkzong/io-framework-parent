package io.code.framework.core.config;

import io.code.framework.core.interceptor.DeleteInterceptor;
import io.code.framework.core.traceid.interceptor.TraceIdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zong
 * @Date: 2021/8/20
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor deleteInterceptor() {
        // 拦截器类
        return new DeleteInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 放到工厂
        registry.addInterceptor(deleteInterceptor());

        registry.addInterceptor(new TraceIdInterceptor()).addPathPatterns("/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
