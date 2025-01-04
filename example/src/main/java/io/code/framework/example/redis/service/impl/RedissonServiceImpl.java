package io.code.framework.example.redis.service.impl;

import io.code.framework.example.redis.service.RedissonService;
import io.code.framework.example.req.UserDto;
import io.code.framework.example.resp.UserVo;
import io.code.framework.redis.annotation.DistributedLock;
import org.springframework.stereotype.Service;

/**
 * @Author: zongz
 * @Date: 2025-01-04
 */
@Service
public class RedissonServiceImpl implements RedissonService {

    @DistributedLock(key = "#userDto.name+#userDto.age")
    @Override
    public UserVo getUser(UserDto userDto) {
        UserVo userVo = new UserVo();
        userVo.setName(userDto.getName());
        userVo.setAge(userDto.getAge());
        userVo.setAmount(userDto.getAmount());
        return userVo;
    }

}
