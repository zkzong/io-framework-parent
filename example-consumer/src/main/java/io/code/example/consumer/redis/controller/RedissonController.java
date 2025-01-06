package io.code.example.consumer.redis.controller;

import io.code.framework.core.entity.ApiResponse;
import io.code.framework.core.entity.ApiResponseUtil;
import io.code.example.consumer.redis.service.RedissonService;
import io.code.framework.example.req.UserDto;
import io.code.framework.example.resp.UserVo;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zongz
 * @Date: 2024-12-28
 */
@RestController
@RequestMapping("/redisson")
public class RedissonController {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedissonService redissonService;

    @GetMapping("/get")
    public ApiResponse<String> get() {
        RLock rLock = redissonClient.getLock("redisson");
        rLock.lock(60, TimeUnit.SECONDS);
        return ApiResponseUtil.success(null);
    }

    @PostMapping("/post")
    public ApiResponse<UserVo> post(@RequestBody UserDto userDto) {
        UserVo userVo = redissonService.getUser(userDto);
        return ApiResponseUtil.success(userVo);
    }
}
