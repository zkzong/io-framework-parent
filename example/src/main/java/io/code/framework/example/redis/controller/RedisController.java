package io.code.framework.example.redis.controller;

import io.code.framework.core.entity.ApiResponse;
import io.code.framework.core.entity.ApiResponseUtil;
import io.code.framework.example.redis.service.RedisService;
import io.code.framework.example.req.UserDto;
import io.code.framework.example.resp.UserVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Resource
    private RedisService redisService;

    @GetMapping("/get")
    public ApiResponse<String> get() {
        redisTemplate.opsForValue().set("redis", "Hello Redis");
        String redis = redisTemplate.opsForValue().get("redis");
        return ApiResponseUtil.success(redis);
    }

    @PostMapping("/post")
    public ApiResponse<UserVo> post(@RequestBody UserDto userDto) {
        UserVo userVo = redisService.getUser(userDto);
        return ApiResponseUtil.success(userVo);
    }
}
