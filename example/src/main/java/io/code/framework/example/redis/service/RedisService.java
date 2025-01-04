package io.code.framework.example.redis.service;

import io.code.framework.example.req.UserDto;
import io.code.framework.example.resp.UserVo;

/**
 * @Author: zongz
 * @Date: 2025-01-04
 */
public interface RedisService {

    UserVo getUser(UserDto userDto);

}
