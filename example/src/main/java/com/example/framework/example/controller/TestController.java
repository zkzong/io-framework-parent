package com.example.framework.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/rest")
    public String rest(@RequestParam String name) {
        log.info("name = {}", name);
        return restTemplate.postForObject("http://127.0.0.1:8081/provider/sayHello?name={name}", null, String.class, name);
    }

}
