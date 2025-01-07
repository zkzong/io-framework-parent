package io.code.example.consumer.controller;

import io.code.example.api.client.ProviderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
@Slf4j
public class ConsumerController {

    @Autowired
    private ProviderClient providerClient;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/sayHello/feign")
    public String sayHelloFeign(@RequestParam String name) {
        log.info("name = {}", name);
        return providerClient.sayHello(name);
    }

    @RequestMapping("/sayHello/resttemplate")
    public String sayHelloRest(@RequestParam String name) {
        log.info("name = {}", name);
        return restTemplate.postForObject("http://127.0.0.1:8081/provider/sayHello?name={name}", null, String.class, name);
    }

}
