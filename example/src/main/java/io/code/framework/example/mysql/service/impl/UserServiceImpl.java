package io.code.framework.example.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.code.framework.example.mysql.entity.User;
import io.code.framework.example.mysql.mapper.UserMapper;
import io.code.framework.example.mysql.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
