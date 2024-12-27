package io.code.framework.example.demo.controller;

import io.code.framework.core.entity.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 基础Controller
 *
 * @Author: zong
 * @Date: 2021/8/12
 */
@RequestMapping("/test")
public interface BaseController {

    @GetMapping("/getParam")
    ApiResponse<String> getParam(@RequestParam String name, @RequestParam Integer age);

}
