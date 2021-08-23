package com.zkzong.framework.core.config;

import com.zkzong.framework.core.interceptor.DeleteInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zong
 * @Date: 2021/8/20
 */
//@Configuration
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
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
