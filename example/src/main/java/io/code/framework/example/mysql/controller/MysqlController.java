package io.code.framework.example.mysql.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zongz
 * @Date: 2024-12-28
 */
@RestController
@RequestMapping("/mysql")
public class MysqlController {

    @RequestMapping("/get")
    public String get() {
        return "Hello Mysql";
    }
}