package io.code.example.consumer.redis.service;

import io.code.framework.example.req.UserDto;
import io.code.framework.example.resp.UserVo;

/**
 * @Author: zongz
 * @Date: 2025-01-04
 */
public interface RedissonService {

    UserVo getUser(UserDto userDto);

}
