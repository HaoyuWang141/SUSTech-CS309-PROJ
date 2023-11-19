package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.*;
import com.ooad.dormitory.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final AllocationStageService allocationStageService;

    @Autowired
    public DormitorySelectionController(DormitoryService dormitoryService,
                                         BuildingService buildingService,
                                         RegionService regionService,
                                         TeamFavoriteDormService teamFavoriteDormService,
                                         AllocationStageService allocationStageService) {
        this.dormitoryService = dormitoryService;
        this.buildingService = buildingService;
        this.regionService = regionService;
        this.teamFavoriteDormService = teamFavoriteDormService;
        this.allocationStageService = allocationStageService;
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


    @PostMapping("/favor")
    public ResponseEntity<?> favorDormitory(@RequestBody StudentAccount studentAccount, @RequestBody Dormitory dormitory) {
        // 判断是否是收藏宿舍阶段
        List<AllocationStage> allocationStageList = allocationStageService.list(new QueryWrapper<AllocationStage>()
                .eq("entryYear", studentAccount.getEntryYear())
                .eq("degree", studentAccount.getDegree())
                .eq("gender", studentAccount.getGender()));
        if (allocationStageList.isEmpty() || allocationStageList.get(0).getStage() != 1) {
            throw new RuntimeException("favor dormitory failed!");
        }
        // 收藏宿舍
        List<TeamFavoriteDorm> teamFavoriteDormList = teamFavoriteDormService.list(new QueryWrapper<TeamFavoriteDorm>()
                .eq("teamId", studentAccount.getTeamId())
                .eq("dormitoryId", dormitory.getDormitoryId()));
        if (teamFavoriteDormList.isEmpty()) {
            TeamFavoriteDorm teamFavoriteDorm = new TeamFavoriteDorm(null, studentAccount.getTeamId(), dormitory.getDormitoryId(), dormitory);
            teamFavoriteDormService.save(teamFavoriteDorm);
        }
        return ResponseEntity.ok().build();
    }


}
