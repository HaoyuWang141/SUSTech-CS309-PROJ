package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Building;
import com.ooad.dormitory.entity.Dormitory;
import com.ooad.dormitory.entity.Region;
import com.ooad.dormitory.entity.TeamFavoriteDorm;
import com.ooad.dormitory.service.BuildingService;
import com.ooad.dormitory.service.DormitoryService;
import com.ooad.dormitory.service.RegionService;
import com.ooad.dormitory.service.TeamFavoriteDormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student/dormitory")
@CrossOrigin
public class DormitorySelectionController {

    private final DormitoryService dormitoryService;
    private final BuildingService buildingService;
    private final RegionService regionService;
    private final TeamFavoriteDormService teamFavoriteDormService;

    @Autowired
    public DormitorySelectionController(DormitoryService dormitoryService,
                                         BuildingService buildingService,
                                         RegionService regionService,
                                         TeamFavoriteDormService teamFavoriteDormService) {
        this.dormitoryService = dormitoryService;
        this.buildingService = buildingService;
        this.regionService = regionService;
        this.teamFavoriteDormService = teamFavoriteDormService;
    }

    @GetMapping("/getRegions")
    public List<Region> queryRegion() {
        return regionService.list();
    }

    @GetMapping("/getBuildings")
    public List<Building> queryBuilding(Integer regionId) {
        return buildingService.list(new QueryWrapper<Building>().eq("regionId", regionId));
    }

    @GetMapping("/getDormitories")
    public List<Dormitory> queryDormitory(Integer buildingId) {
        return dormitoryService.list(new QueryWrapper<Dormitory>().eq("buildingId", buildingId));
    }

    @GetMapping("/getFavoriteList")
    public List<Dormitory> getFavoriteDormitories(Integer teamId) {
        return teamFavoriteDormService.list(new QueryWrapper<TeamFavoriteDorm>().eq("teamId", teamId))
                .stream().map(TeamFavoriteDorm::getDormitory).collect(Collectors.toList());
    }

    @GetMapping("/getDormitory")
    public Dormitory getDormitory(Integer dormitoryId) {
        return dormitoryService.getById(dormitoryId);
    }




}
