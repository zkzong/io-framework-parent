package io.code.framework.example.controller;

import io.code.framework.core.annotation.Delete;
import io.code.framework.core.req.Req;
import io.code.framework.core.resp.Resp;
import io.code.framework.core.resp.Result;
import io.code.framework.core.resp.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public Result<Resp> post(@RequestBody @Validated Req req) {
        log.info("post");
        Resp resp = new Resp();
        BeanUtils.copyProperties(req, resp);
        return ResultUtil.success(resp);
    }

    /**
     * post请求，入参使用@RequestParam
     *
     * @param name
     * @param age
     * @return
     */
    @PostMapping("/postParam")
    public Result<String> postParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("postParam");
        return ResultUtil.success("Hello World, name=" + name + ", age=" + age);
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
    public Result<String> getParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("getParam");
        return ResultUtil.success("Hello World, name=" + name + ", age=" + age);
    }

    /**
     * get请求，如果入参是对象且没加注解，需要传入form
     *
     * @param req
     * @return
     */
    @GetMapping("getForm")
    public Result<String> getForm(Req req) {
        log.info("getForm");
        return ResultUtil.success(req.toString());
    }

    /**
     * get请求，如果入参是对象且加注解@RequestBody，则传入json
     *
     * @param req
     * @return
     */
    @GetMapping("getBody")
    public Result<String> getBody(@RequestBody Req req) {
        log.info("getBody");
        return ResultUtil.success(req.toString());
    }
}
