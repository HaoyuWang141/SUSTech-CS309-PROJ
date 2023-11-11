package com.ooad.dormitory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ooad.dormitory.entity.AllocationRelation;
import com.ooad.dormitory.mapper.AllocationRelationMapper;
import com.ooad.dormitory.service.AllocationRelationService;
import org.springframework.stereotype.Service;

@Service
public class AllocationRelationServiceImpl extends ServiceImpl<AllocationRelationMapper, AllocationRelation> implements AllocationRelationService {

    private final AllocationRelationMapper allocationRelationMapper;

    public AllocationRelationServiceImpl(AllocationRelationMapper allocationRelationMapper) {
        this.allocationRelationMapper = allocationRelationMapper;
    }
}
