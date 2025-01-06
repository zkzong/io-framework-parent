package io.code.example.consumer.service.impl;

import io.code.example.consumer.service.DemoService;
import io.code.framework.core.annotation.Delete;
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
