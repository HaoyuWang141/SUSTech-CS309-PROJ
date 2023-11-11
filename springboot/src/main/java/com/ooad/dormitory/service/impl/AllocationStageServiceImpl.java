package com.ooad.dormitory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ooad.dormitory.entity.AllocationStage;
import com.ooad.dormitory.mapper.AllocationStageMapper;
import com.ooad.dormitory.service.AllocationStageService;
import org.springframework.stereotype.Service;

@Service
public class AllocationStageServiceImpl extends ServiceImpl<AllocationStageMapper, AllocationStage> implements AllocationStageService {

        private final AllocationStageMapper allocationStageMapper;

        public AllocationStageServiceImpl(AllocationStageMapper allocationStageMapper) {
            this.allocationStageMapper = allocationStageMapper;
        }
}
