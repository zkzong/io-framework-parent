package com.example.framework.example.controller;

import com.example.framework.core.resp.Resp;
import com.example.framework.core.resp.RespUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常类，使用@RestControllerAdvice统一处理返回结果
 *
 * @Author: zongz
 * @Date: 2024/9/26
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    /**
     * 除零异常
     *
     * @return
     */
    @RequestMapping("/zero")
    public Resp zero() {
        int i = 1 / 0;
        return RespUtil.success("ok");
    }

}
