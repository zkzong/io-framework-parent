package io.code.example.consumer.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.code.example.consumer.mysql.entity.User;
import io.code.example.consumer.mysql.mapper.UserMapper;
import io.code.example.consumer.mysql.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
