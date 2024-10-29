package io.code.framework.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Author: zong
 * @Date: 2021/8/2
 */
@ServletComponentScan(basePackages = {"io.code.framework"})
@SpringBootApplication(scanBasePackages = {"io.code.framework"})
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

}
