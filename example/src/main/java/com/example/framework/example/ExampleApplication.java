package com.example.framework.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

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
}
