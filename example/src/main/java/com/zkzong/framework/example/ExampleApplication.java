package com.zkzong.framework.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: zong
 * @Date: 2021/8/2
 */
@SpringBootApplication(scanBasePackages = {"com.zkzong.framework"})
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
}
