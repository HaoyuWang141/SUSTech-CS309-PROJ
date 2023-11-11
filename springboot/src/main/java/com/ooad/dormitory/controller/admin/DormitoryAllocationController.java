package com.ooad.dormitory.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.AllocationRelation;
import com.ooad.dormitory.entity.AllocationStage;
import com.ooad.dormitory.service.AllocationRelationService;
import com.ooad.dormitory.service.AllocationStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/allocation")
public class DormitoryAllocationController {

    private final AllocationRelationService allocationRelationService;
    private final AllocationStageService allocationStageService;

    @Autowired
    public DormitoryAllocationController(AllocationRelationService allocationRelationService, AllocationStageService allocationStageService) {
        this.allocationRelationService = allocationRelationService;
        this.allocationStageService = allocationStageService;
    }

    @PostMapping("/relate")
    public ResponseEntity<?> relateDormitory(@RequestBody Integer entryYear, @RequestBody Integer degree, @RequestBody Integer gender, @RequestBody List<Integer> dormitoryIdList) {
        List<AllocationRelation> allocationRelationList = new ArrayList<>();
        for (Integer dormitoryId : dormitoryIdList) {
            allocationRelationList.add(new AllocationRelation(null, entryYear, degree, gender, dormitoryId));
        }
        allocationRelationService.saveBatch(allocationRelationList);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getRelation")
    public List<Integer> getDormitoryRelation(Integer entryYear, Integer degree, Integer gender) {
        QueryWrapper<AllocationRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entry_year", entryYear).eq("degree", degree).eq("gender", gender);
        List<AllocationRelation> allocationRelationList = allocationRelationService.list(queryWrapper);
        List<Integer> dormitoryList = new ArrayList<>();
        for (AllocationRelation allocationRelation : allocationRelationList) {
            dormitoryList.add(allocationRelation.getDormitoryId());
        }
        return dormitoryList;
    }

    @PostMapping("/setState")
    public ResponseEntity<?> setState(Integer entryYear, Integer degree, Integer gender, Integer state) {
        AllocationStage allocationStage = new AllocationStage(null, entryYear, degree, gender, state);
        allocationStageService.saveOrUpdate(allocationStage);
        return ResponseEntity.ok().build();
    }
}
