package com.zkzong.framework.example.controller;

import com.zkzong.framework.core.annotation.Delete;
import com.zkzong.framework.core.req.Req;
import com.zkzong.framework.core.resp.Resp;
import com.zkzong.framework.example.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class DemoController implements BaseController {

    @Autowired
    private DemoService demoService;

    @PostMapping("/post")
    public Resp post(@RequestBody @Validated Req req) {
        log.info("post");
        Resp resp = new Resp();
        return resp;
    }

    @Override
    public String get(@RequestParam String name, @RequestParam Integer age) {
        log.info("get");
        return "Hello World, name=" + name + ", age=" + age;
    }

    @Delete
    @Override
    public String map(@RequestParam String name) {
        log.info("map");
        final String hello = demoService.sayHello();
        return hello + ", " + name;
    }
}
