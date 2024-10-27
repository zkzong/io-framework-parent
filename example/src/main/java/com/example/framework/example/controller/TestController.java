package com.example.framework.example.controller;

import com.example.framework.core.annotation.Delete;
import com.example.framework.core.req.Req;
import com.example.framework.core.resp.Resp;
import com.example.framework.core.resp.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 */
@RestController
@Slf4j
public class TestController implements BaseController {

    /**
     * post请求，入参使用@RequestBody，且加校验
     *
     * @param req
     * @return
     */
    @PostMapping("/post")
    public Resp<Req> post(@RequestBody @Validated Req req) {
        log.info("post");
        return RespUtil.success(req);
    }

    /**
     * post请求，入参使用@RequestParam
     *
     * @param name
     * @param age
     * @return
     */
    @PostMapping("/postParam")
    public Resp<String> postParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("postParam");
        return RespUtil.success("Hello World, name=" + name + ", age=" + age);
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
    public Resp<String> getParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("getParam");
        return RespUtil.success("Hello World, name=" + name + ", age=" + age);
    }

    /**
     * get请求，如果入参是对象且没加注解，需要传入form
     *
     * @param req
     * @return
     */
    @GetMapping("getForm")
    public Resp<String> getForm(Req req) {
        log.info("getForm");
        return RespUtil.success(req.toString());
    }

    /**
     * get请求，如果入参是对象且加注解@RequestBody，则传入json
     *
     * @param req
     * @return
     */
    @GetMapping("getBody")
    public Resp<String> getBody(@RequestBody Req req) {
        log.info("getBody");
        return RespUtil.success(req.toString());
    }
}
