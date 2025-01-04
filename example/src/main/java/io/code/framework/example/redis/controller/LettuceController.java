package io.code.framework.example.redis.controller;

import io.code.framework.core.entity.ApiResponse;
import io.code.framework.core.entity.ApiResponseUtil;
import io.code.framework.redis.utils.LettuceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zongz
 * @Date: 2024-12-28
 */
@RestController
@RequestMapping("/lettuce")
public class LettuceController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private LettuceUtil<String> lettuceUtil;

    @GetMapping("/rest")
    public ApiResponse<String> rest() {
        redisTemplate.opsForValue().set("lettuce", "Hello Lettuce");
        String lettuce = redisTemplate.opsForValue().get("lettuce");
        return ApiResponseUtil.success(lettuce);
    }

    @GetMapping("/util")
    public ApiResponse<String> util() {
        lettuceUtil.set("lettuce", "Hello Lettuce", 60);
        String lettuce = lettuceUtil.get("lettuce");
        return ApiResponseUtil.success(lettuce);
    }

}
