package io.code.framework.example.demo.controller;

import io.code.framework.core.annotation.Delete;
import io.code.framework.core.entity.ApiResponse;
import io.code.framework.core.entity.ApiResponseUtil;
import io.code.framework.example.req.UserDto;
import io.code.framework.example.resp.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ApiResponse<UserVo> post(@RequestBody @Validated UserDto req) {
        log.info("post");
        UserVo resp = new UserVo();
        BeanUtils.copyProperties(req, resp);
        return ApiResponseUtil.success(resp);
    }

    /**
     * post请求，入参使用@RequestParam
     *
     * @param name
     * @param age
     * @return
     */
    @PostMapping("/postParam")
    public ApiResponse<String> postParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("postParam");
        return ApiResponseUtil.success("Hello World, name=" + name + ", age=" + age);
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
    public ApiResponse<String> getParam(@RequestParam String name, @RequestParam Integer age) {
        log.info("getParam");
        return ApiResponseUtil.success("Hello World, name=" + name + ", age=" + age);
    }

    /**
     * get请求，如果入参是对象且没加注解，需要传入form
     *
     * @param req
     * @return
     */
    @GetMapping("/getForm")
    public ApiResponse<String> getForm(UserDto req) {
        log.info("getForm");
        return ApiResponseUtil.success(req.toString());
    }

    /**
     * get请求，如果入参是对象且加注解@RequestBody，则传入json
     *
     * @param req
     * @return
     */
    @GetMapping("/getBody")
    public ApiResponse<String> getBody(@RequestBody UserDto req) {
        log.info("getBody");
        return ApiResponseUtil.success(req.toString());
    }

    // todo
    @PostMapping("/path/{name}")
    public ApiResponse<String> path(@PathVariable String name) {
        return ApiResponseUtil.success(name);
    }
}
