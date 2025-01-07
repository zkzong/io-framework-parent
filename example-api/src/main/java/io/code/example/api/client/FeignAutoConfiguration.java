package io.code.example.api.client;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: zongz
 * @Date: 2024/11/16
 */
@EnableFeignClients(basePackages = "io.code.example.api.client")
public class FeignAutoConfiguration {
}
