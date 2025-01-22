package io.code.example.provider.controller;

import io.code.example.api.req.UserDto;
import io.code.example.api.resp.UserVo;
import io.code.framework.common.entity.ApiResponse;
import io.code.framework.common.entity.ApiResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign/provider")
@Slf4j
public class FeignProviderController {

    @PostMapping("/user")
    public ApiResponse<UserVo> sayHello(@RequestBody UserDto userDto) {
        log.info("userDto = {}", userDto);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        return ApiResponseUtil.success(userVo);
    }

}
