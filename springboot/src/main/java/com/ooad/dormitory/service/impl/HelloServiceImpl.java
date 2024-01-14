package com.ooad.dormitory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ooad.dormitory.entity.Hello;
import com.ooad.dormitory.mapper.HelloMapper;
import com.ooad.dormitory.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl extends ServiceImpl<HelloMapper, Hello> implements HelloService {

    private final HelloMapper helloMapper;

    @Autowired
    public HelloServiceImpl(HelloMapper helloMapper) {
        this.helloMapper = helloMapper;
    }

}
