package com.ooad.dormitory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ooad.dormitory.entity.StudentAccount;

import java.util.List;


public interface StudentAccountService extends IService<StudentAccount> {
   public List<StudentAccount> getAllStudentAccount();
}
