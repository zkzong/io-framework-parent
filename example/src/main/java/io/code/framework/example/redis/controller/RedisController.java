package io.code.framework.example.redis.controller;

import io.code.framework.core.entity.ApiResponse;
import io.code.framework.core.entity.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zongz
 * @Date: 2024-12-28
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/get")
    public ApiResponse<String> get() {
        redisTemplate.opsForValue().set("redis", "Hello Redis");
        String redis = redisTemplate.opsForValue().get("redis");
        return ApiResponseUtil.success(redis);
    }
}
