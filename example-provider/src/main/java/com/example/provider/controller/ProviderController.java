package com.example.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return "hello, " + name;
    }

}
