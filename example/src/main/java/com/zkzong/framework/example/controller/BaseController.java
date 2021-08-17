package com.zkzong.framework.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: zong
 * @Date: 2021/8/12
 */
@RequestMapping("/demo")
public interface BaseController {

    @GetMapping("/get")
    String get(@RequestParam String name, @RequestParam Integer age);

    @RequestMapping("/map")
    String map(@RequestParam String name);

}
