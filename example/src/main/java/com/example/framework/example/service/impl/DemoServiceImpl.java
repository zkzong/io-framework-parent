package com.example.framework.example.service.impl;

import com.example.framework.core.annotation.Delete;
import com.example.framework.example.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @Author: zong
 * @Date: 2021/8/11
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Delete
    @Override
    public String sayHello() {
        return "Hello World";
    }
}
