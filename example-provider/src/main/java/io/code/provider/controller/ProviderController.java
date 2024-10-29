package io.code.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
@Slf4j
public class ProviderController {

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        log.info("name = {}", name);
        return "hello, " + name;
    }

}
