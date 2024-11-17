package io.code.framework.example.controller;

import io.code.framework.core.resp.Result;
import io.code.framework.core.resp.ResultUtil;
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
    public Result zero() {
        int i = 1 / 0;
        return ResultUtil.success("ok");
    }

}
