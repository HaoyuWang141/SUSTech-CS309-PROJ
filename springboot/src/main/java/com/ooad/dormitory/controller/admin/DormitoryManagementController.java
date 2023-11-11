package com.ooad.dormitory.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Building;
import com.ooad.dormitory.entity.Dormitory;
import com.ooad.dormitory.entity.Region;
import com.ooad.dormitory.service.BuildingService;
import com.ooad.dormitory.service.DormitoryService;
import com.ooad.dormitory.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/dormitory")
@CrossOrigin
public class DormitoryManagementController {

    private final DormitoryService dormitoryService;
    private final BuildingService buildingService;
    private final RegionService regionService;

    @Autowired
    public DormitoryManagementController(DormitoryService dormitoryService,
                                         BuildingService buildingService,
                                         RegionService regionService) {
        this.dormitoryService = dormitoryService;
        this.buildingService = buildingService;
        this.regionService = regionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDormitory(@RequestBody List<Dormitory> dormitoryList) {
        dormitoryService.saveBatch(dormitoryList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<?> deleteDormitory(@RequestBody List<Integer> dormitoryIdList) {
        dormitoryService.removeByIds(dormitoryIdList);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDormitory(@RequestBody List<Dormitory> dormitoryList) {
        dormitoryService.updateBatchById(dormitoryList);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public List<Dormitory> queryDormitory(String type, Integer id) {
        List<Dormitory> dormitoryList = new ArrayList<>();
        switch (type) {
            case "regionId":
                List<Building> buildingIdList = buildingService.list(new QueryWrapper<Building>().eq("regionId", id));
                for (Building building : buildingIdList) {
                    dormitoryList.addAll(dormitoryService.list(new QueryWrapper<Dormitory>().eq("buildingId", building.getBuildingId())));
                }
                break;
            case "buildingId":
                dormitoryList.addAll(dormitoryService.list(new QueryWrapper<Dormitory>().eq("buildingId", id)));
                break;
            case "dormitoryId":
                dormitoryList.add(dormitoryService.getById(id));
                break;
        }
        return dormitoryList;
    }

    @GetMapping("/getRegion")
    public List<Region> queryRegion() {
        return regionService.list();
    }

    @GetMapping("/getBuilding")
    public List<Building> queryBuilding(Integer regionId) {
        return buildingService.list(new QueryWrapper<Building>().eq("regionId", regionId));
    }

    @PutMapping("/updateBuilding")
    public ResponseEntity<?> updateBuilding(@RequestBody Building building) {
        buildingService.updateById(building);
        return ResponseEntity.ok().build();
    }
}
