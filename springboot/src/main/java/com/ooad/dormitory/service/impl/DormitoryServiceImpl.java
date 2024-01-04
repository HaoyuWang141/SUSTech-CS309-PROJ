package com.ooad.dormitory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ooad.dormitory.entity.Dormitory;
import com.ooad.dormitory.mapper.DormitoryMapper;
import com.ooad.dormitory.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory> implements DormitoryService {

        private final DormitoryMapper dormitoryMapper;

        @Autowired
        public DormitoryServiceImpl(DormitoryMapper dormitoryMapper) {
            this.dormitoryMapper = dormitoryMapper;
        }

    @Override
    public List<Dormitory> getDormitories() {
        return dormitoryMapper.getDormitories();
    }
}
