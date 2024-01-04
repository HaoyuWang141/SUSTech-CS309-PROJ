package com.ooad.dormitory.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Building;
import com.ooad.dormitory.entity.Dormitory;
import com.ooad.dormitory.entity.Layout;
import com.ooad.dormitory.entity.Region;
import com.ooad.dormitory.service.BuildingService;
import com.ooad.dormitory.service.DormitoryService;
import com.ooad.dormitory.service.LayoutService;
import com.ooad.dormitory.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//import io.swagger.v3.oas.annotations.

@RestController
@RequestMapping("/admin/dormitory")
@CrossOrigin
public class DormitoryManagementController {

    private final DormitoryService dormitoryService;
    private final BuildingService buildingService;
    private final RegionService regionService;
    private final LayoutService layoutService;

    @Autowired
    public DormitoryManagementController(DormitoryService dormitoryService,
                                         BuildingService buildingService,
                                         RegionService regionService,
                                         LayoutService layoutService) {
        this.dormitoryService = dormitoryService;
        this.buildingService = buildingService;
        this.regionService = regionService;
        this.layoutService = layoutService;
    }

    @Operation(summary = "create dormitory",
            description = """
                    创建宿舍, dormitory 需要依赖于 building 和 layout
                    id字段不存在即可，数据库会自增创建
                    
                    创建时必填的字段：floor，roomNumber，
                    
                    可以发现同时有building对象和buildingId两个字段， 解释如下：
                    1. building:
                    若 buildingId 存在, 则直接使用该 buildingId;
                    若 buildingId 不存在, 则使用 building.id; 若 building.id 不存在, 则创建 building, 并使用 building.id
                    注: 创建 building 时, 需要依赖于 region, 逻辑同上.

                    2. layout:
                    若 layoutId 存在, 则直接使用该 layoutId;
                    若 layoutId 不存在, 则使用 layout.id; 若 layout.id 不存在, 则创建 layout, 并使用 layout.id""")
    @PostMapping("/create/dormitory")
    public ResponseEntity<?> createDormitory(@RequestBody List<Dormitory> dormitoryList) {
        /**
         * dormitory 需要依赖于 building 和 layout
         * 1. building:
         * 若 buildingId 存在, 则直接使用该 buildingId;
         * 若 buildingId 不存在, 则使用 building.id; 若 building.id 不存在, 则创建 building, 并使用 building.id
         * 注: 创建 building 时, 需要依赖于 region, 逻辑同上.
         *
         * 2. layout:
         * 若 layoutId 存在, 则直接使用该 layoutId;
         * 若 layoutId 不存在, 则使用 layout.id; 若 layout.id 不存在, 则创建 layout, 并使用 layout.id
         */
        dormitoryList.forEach(dormitory -> {
            if (dormitory.getBuildingId() == null && dormitory.getBuilding() == null) {
                throw new RuntimeException("Exist dormitory: both BuildingId and Building are null");
            }
            if (dormitory.getBuildingId() == null) {
                Building building = dormitory.getBuilding();
                maintainBuilding(building);
                dormitory.setBuildingId(building.getBuildingId());
            }

            if (dormitory.getLayoutId() == null && dormitory.getLayout() == null) {
                throw new RuntimeException("Exist dormitory: both LayoutId and Layout are null");
            }
            if (dormitory.getLayoutId() == null) {
                Layout layout = dormitory.getLayout();
                if (layout.getLayoutId() == null) {
                    layoutService.save(layout);
                }
                dormitory.setLayoutId(layout.getLayoutId());
            }

        });
        dormitoryService.saveBatch(dormitoryList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/building")
    public ResponseEntity<?> createBuilding(@RequestBody List<Building> buildingList) {
        buildingList.forEach(this::maintainBuilding);
        buildingService.saveBatch(buildingList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/region")
    public ResponseEntity<?> createRegion(@RequestBody List<Region> regionList) {
        regionService.saveBatch(regionList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/layout")
    public ResponseEntity<?> createLayout(@RequestBody List<Layout> layoutList) {
        layoutService.saveBatch(layoutList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/dormitory")
    public ResponseEntity<?> deleteDormitory(@RequestBody List<Integer> dormitoryIdList) {
        dormitoryService.removeByIds(dormitoryIdList);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/dormitory")
    public ResponseEntity<?> updateDormitory(@RequestBody List<Dormitory> dormitoryList) {
        dormitoryService.updateBatchById(dormitoryList);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/region")
    public ResponseEntity<?> updateRegion(@RequestBody Region region) {
        regionService.updateById(region);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/building")
    public ResponseEntity<?> updateBuilding(@RequestBody Building building) {
        buildingService.updateById(building);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/layout")
    public ResponseEntity<?> updateLayout(@RequestBody Layout layout) {
        layoutService.updateById(layout);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/dormitory")
    @Operation(summary = "get dormitory",
            description = """
                    获取宿舍, type 为 region, building, dormitory 之一, id 为对应的 id
                    type 为 region 时, 返回该 region 下的所有宿舍
                    type 为 building 时, 返回该 building 下的所有宿舍
                    type 为 dormitory 时, 返回该 dormitory (数据结构为 List<Dormitory>，但只有一个元素)
                    """)
    public List<Dormitory> queryDormitory(String type, Integer id) {
        List<Dormitory> dormitoryList = new ArrayList<>();
        switch (type) {
            case "region":
                List<Building> buildingIdList = buildingService.list(new QueryWrapper<Building>().eq("region_id", id));
                for (Building building : buildingIdList) {
                    dormitoryList.addAll(dormitoryService.list(new QueryWrapper<Dormitory>().eq("building_id", building.getBuildingId())));
                }
                break;
            case "building":
                dormitoryList.addAll(dormitoryService.list(new QueryWrapper<Dormitory>().eq("building_id", id)));
                break;
            case "dormitory":
                dormitoryList.add(dormitoryService.getById(id));
                break;
        }
        return dormitoryList;
    }

    @GetMapping("/get/region")
    public List<Region> queryRegion() {
        return regionService.list();
    }

    @GetMapping("/get/building")
    public List<Building> queryBuilding(Integer regionId) {
        return buildingService.list(new QueryWrapper<Building>().eq("regionId", regionId));
    }

    @GetMapping("/get/layout")
    public List<Layout> queryLayout() {
        return layoutService.list();
    }

    private void maintainBuilding(Building building) {
        if (building.getRegionId() == null && building.getRegion() == null) {
            throw new RuntimeException("Exist building: both RegionId and Region are null");
        }

        if (building.getRegionId() == null) {
            Region region = building.getRegion();
            if (region.getRegionId() == null) {
                regionService.save(region);
            }
            building.setRegionId(region.getRegionId());
        }

        if (building.getBuildingId() == null) {
            buildingService.save(building);
        }
    }
}
