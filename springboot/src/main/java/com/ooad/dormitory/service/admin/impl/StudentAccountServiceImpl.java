package com.ooad.dormitory.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.mapper.StudentAccountMapper;
import com.ooad.dormitory.service.admin.StudentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentAccountServiceImpl extends ServiceImpl<StudentAccountMapper, StudentAccount> implements StudentAccountService {

    private final StudentAccountMapper studentAccountMapper;

    @Autowired
    public StudentAccountServiceImpl(StudentAccountMapper studentAccountMapper) {
        this.studentAccountMapper = studentAccountMapper;
    }
}
