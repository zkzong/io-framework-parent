package com.zkzong.framework.example.controller;

import com.zkzong.framework.example.req.DemoReq;
import com.zkzong.framework.example.resp.DemoResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController implements BaseController {

    @PostMapping("/post")
    public DemoResp post(@RequestBody @Validated DemoReq demoReq) {
        log.info("post");
        DemoResp demoResp = new DemoResp();
        return demoResp;
    }

    //@GetMapping("/get")
    @Override
    public String get(@RequestParam String name, @RequestParam Integer age) {
        log.info("get");
        return "Hello World, name=" + name + ", age=" + age;
    }

    //@RequestMapping("/map")
    @Override
    public String map(@RequestParam String name) {
        log.info("map");
        return name;
    }
}
