package com.example.framework.example.controller;

import com.example.framework.core.annotation.ApiVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zong
 * @Date: 2021/9/3
 */
@ApiVersion(1)
@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @GetMapping("/get")
    public String get() {
        log.info("api get");
        return "api";
    }

}
