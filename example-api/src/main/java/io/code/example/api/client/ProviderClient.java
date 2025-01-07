package io.code.example.api.client;

import io.code.example.api.req.UserDto;
import io.code.example.api.resp.UserVo;
import io.code.framework.core.entity.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "example-provider")
public interface ProviderClient {

    @PostMapping("/provider/user")
    ApiResponse<UserVo> user(@RequestBody UserDto userDto);

}
