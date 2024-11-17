package io.code.framework.core.config;

import io.code.framework.core.traceid.interceptor.OkHttpTraceIdInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * okhttp配置类
 *
 * @Author: zongz
 * @Date: 2024/11/17
 */
@Configuration
public class OkHttpConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new OkHttpTraceIdInterceptor())
                .build();
        return okHttpClient;
    }

}
