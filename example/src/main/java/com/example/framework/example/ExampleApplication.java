package com.example.framework.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zong
 * @Date: 2021/8/2
 */
@ServletComponentScan(basePackages = {"com.example.framework"})
@SpringBootApplication(scanBasePackages = {"com.example.framework"})
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.setInterceptors(Arrays.asList(new RestTemplateTraceIdInterceptor()));
        return restTemplate;
    }
}
