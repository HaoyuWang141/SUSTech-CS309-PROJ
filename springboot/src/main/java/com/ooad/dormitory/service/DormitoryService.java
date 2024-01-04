package com.ooad.dormitory.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ooad.dormitory.entity.Dormitory;

import java.sql.Wrapper;
import java.util.List;

public interface DormitoryService extends IService<Dormitory> {

    public List<Dormitory> getDormitories();
}
