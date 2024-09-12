package com.example.framework.example.controller;

import com.example.framework.core.annotation.Delete;
import com.example.framework.core.req.Req;
import com.example.framework.core.resp.Resp;
import com.example.framework.example.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class DemoController implements BaseController {

    @Autowired
    private DemoService demoService;

    /**
     * post请求，入参使用@RequestBody，且加校验
     *
     * @param req
     * @return
     */
    @PostMapping("/post")
    public Resp post(@RequestBody @Validated Req req) {
        log.info("post");
        Resp resp = new Resp();
        resp.setData(req);
        return resp;
    }

    /**
     * get请求，入参使用@RequestParam
     *
     * @param name
     * @param age
     * @return
     */
    @Delete
    @Override
    public String getParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("getParam");
        return "Hello World, name=" + name + ", age=" + age;
    }

    /**
     * get请求，如果入参是对象且没加注解，需要传入form
     *
     * @param req
     * @return
     */
    @GetMapping("getForm")
    public String getForm(Req req) {
        log.info("getForm");
        return req.toString();
    }

    /**
     * get请求，如果入参是对象且加注解@RequestBody，则传入json
     *
     * @param req
     * @return
     */
    @GetMapping("getBody")
    public String getBody(@RequestBody Req req) {
        log.info("getBody");
        return req.toString();
    }
}
