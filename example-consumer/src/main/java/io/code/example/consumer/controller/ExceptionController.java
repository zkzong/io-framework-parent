package io.code.example.consumer.controller;

import io.code.framework.common.entity.ApiResponse;
import io.code.framework.common.entity.ApiResponseUtil;
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
    public ApiResponse zero() {
        int i = 1 / 0;
        return ApiResponseUtil.success("ok");
    }

}
