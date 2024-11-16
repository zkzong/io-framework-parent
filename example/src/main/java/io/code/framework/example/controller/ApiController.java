package io.code.framework.example.controller;

import io.code.framework.core.annotation.ApiVersion;
import io.code.framework.core.resp.Resp;
import io.code.framework.core.resp.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过注解@ApiVersion修改uri请求路径
 *
 * @Author: zong
 * @Date: 2021/9/3
 */
@ApiVersion(1)
@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @GetMapping("/get")
    public Resp<String> get() {
        log.info("api get");
        return RespUtil.success("api");
    }

}