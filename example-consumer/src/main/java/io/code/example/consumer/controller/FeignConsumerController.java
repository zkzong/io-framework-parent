package io.code.example.consumer.controller;

import io.code.example.api.client.ProviderClient;
import io.code.example.api.req.UserDto;
import io.code.example.api.resp.UserVo;
import io.code.framework.core.entity.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign/consumer")
@Slf4j
public class FeignConsumerController {

    @Autowired
    private ProviderClient providerClient;

    @PostMapping("/user")
    public ApiResponse<UserVo> userFeign(@RequestBody UserDto userDto) {
        log.info("userDto = {}", userDto);
        return providerClient.user(userDto);
    }

}
