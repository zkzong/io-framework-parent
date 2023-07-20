package com.example.consumer.controller;

import com.example.consumer.client.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ProviderClient providerClient;

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return providerClient.sayHello(name);
    }

}
