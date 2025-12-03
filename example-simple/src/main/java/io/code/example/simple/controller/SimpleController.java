package io.code.example.simple.controller;

import io.code.framework.common.entity.ApiResponse;
import io.code.framework.common.entity.ApiResponseUtil;
import lombok.extern.slf4j.Slf4j;
import io.code.example.simple.entity.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple")
@Slf4j
public class SimpleController {

    @PostMapping("/post")
    public ApiResponse post() {
        return ApiResponseUtil.success(null);
    }

    @PostMapping("/postBody")
    public ApiResponse postBody(@RequestBody UserDto userDto) {
        log.info("userDto : {}", userDto);
        return ApiResponseUtil.success(userDto);
    }

    @PostMapping("/postParam")
    public ApiResponse postParam(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        log.info("name : {}, age : {}", name, age);
        return ApiResponseUtil.success(name);
    }

    @GetMapping("/getBody")
    public ApiResponse getBody(UserDto userDto) {
        return ApiResponseUtil.success(userDto);
    }

    @GetMapping("/get")
    public ApiResponse get() {
        return ApiResponseUtil.success(null);
    }
}
