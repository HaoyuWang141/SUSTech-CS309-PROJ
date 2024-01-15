package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.*;
import com.ooad.dormitory.exception.BadRequestException;
import com.ooad.dormitory.exception.NotFoundException;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.processor.RequestSender;
import com.ooad.dormitory.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student/dormitory")
@CrossOrigin
public class DormitorySelectionController {

    private final StudentAccountService studentAccountService;
    private final DormitoryService dormitoryService;
    private final BuildingService buildingService;
    private final RegionService regionService;
    private final TeamFavoriteDormService teamFavoriteDormService;
    private final AllocationStageService allocationStageService;
    private final AllocationRelationService allocationRelationService;
    private final TeamService teamService;
    private final AuthenticationMapper authenticationMapper;
    private final LayoutService layoutService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RequestSender requestSender;

    @Autowired
    public DormitorySelectionController(StudentAccountService studentAccountService, DormitoryService dormitoryService, BuildingService buildingService, RegionService regionService, TeamFavoriteDormService teamFavoriteDormService, AllocationStageService allocationStageService, AllocationRelationService allocationRelationService, TeamService teamService, AuthenticationMapper authenticationMapper, LayoutService layoutService) {
        this.studentAccountService = studentAccountService;
        this.dormitoryService = dormitoryService;
        this.buildingService = buildingService;
        this.regionService = regionService;
        this.teamFavoriteDormService = teamFavoriteDormService;
        this.allocationStageService = allocationStageService;
        this.allocationRelationService = allocationRelationService;
        this.teamService = teamService;
        this.authenticationMapper = authenticationMapper;
        this.layoutService = layoutService;
    }

    @GetMapping("/getRegions")
    public List<Region> queryRegion() {
        return regionService.list();
    }

    @GetMapping("/getBuildings")
    public List<Building> queryBuilding(Integer regionId) {
        return buildingService.list(new QueryWrapper<Building>().eq("region_id", regionId));
    }

    @GetMapping("/getDormitories")
    public List<Dormitory> queryDormitory(Integer buildingId, Integer floor, String roomNumber) {
        List<Dormitory> dormitoryList = dormitoryService.getDormitories();
        System.out.println(roomNumber);
        if (roomNumber != null) {
            return dormitoryList
                    .stream()
                    .filter(dormitory -> dormitory.getBuildingId().equals(buildingId) && dormitory.getFloor().equals(floor) && dormitory.getRoomNumber().equals(roomNumber))
                    .collect(Collectors.toList());
        }
        if (floor != null) {
            return dormitoryList.stream().filter(dormitory -> dormitory.getBuildingId().equals(buildingId) && dormitory.getFloor().equals(floor)).collect(Collectors.toList());
        }
        return dormitoryList.stream().filter(dormitory -> dormitory.getBuildingId().equals(buildingId)).collect(Collectors.toList());
    }

    @GetMapping("/getFavoriteList")
    public List<Dormitory> getFavoriteDormitories(Integer teamId) {
        List<Integer> dormitoryIdList = teamFavoriteDormService.list(new QueryWrapper<TeamFavoriteDorm>().eq("team_id", teamId))
                .stream().map(TeamFavoriteDorm::getDormitoryId).toList();
        List<Dormitory> dormitoryList = new ArrayList<>();
        for (Dormitory dormitory :  dormitoryService.getDormitories()) {
            if (dormitoryIdList.contains(dormitory.getDormitoryId())) {
                dormitoryList.add(dormitory);
            }
        }
        return dormitoryList;
    }

    @GetMapping("/getDormitory")
    public Dormitory getDormitory(Integer dormitoryId) {
        return dormitoryService.getById(dormitoryId);
    }

    @GetMapping("/getDormitory2")
    public Dormitory getDormitory(String buildingId, String roomNumber) {
        return dormitoryService.getOne(new QueryWrapper<Dormitory>()
                .eq("building_id", buildingId)
                .eq("room_number", roomNumber));
    }

    @PostMapping("/favor2")
    public ResponseEntity<?> favorDormitory2(String studentAccountId, Integer dormitoryId) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        Dormitory dormitory = dormitoryService.getById(dormitoryId);
        if (studentAccount == null || dormitory == null) {
            throw new BadRequestException("student or dormitory not exist");
        }
        if (studentAccount.getTeamId() == null) {
            throw new BadRequestException("student not in a team");
        }

        // 判断是否是收藏宿舍阶段
        List<AllocationStage> allocationStageList = allocationStageService.list(new QueryWrapper<AllocationStage>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender())
                .eq("stage", 1));
        if (allocationStageList.isEmpty()) {
            throw new BadRequestException("favor dormitory failed! (wrong time)");
        }
        Timestamp startTime = allocationStageList.get(0).getStartTime();
        Timestamp endTime = allocationStageList.get(0).getEndTime();
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        if (nowTime.compareTo(startTime) < 0 || nowTime.compareTo(endTime) > 0) {
            throw new BadRequestException("favor dormitory failed! (wrong time)");
        }

        // 收藏宿舍
        List<TeamFavoriteDorm> teamFavoriteDormList = teamFavoriteDormService.list(new QueryWrapper<TeamFavoriteDorm>()
                .eq("team_id", studentAccount.getTeamId())
                .eq("dormitory_id", dormitory.getDormitoryId()));
        if (teamFavoriteDormList.isEmpty()) {
            TeamFavoriteDorm teamFavoriteDorm = new TeamFavoriteDorm(null, studentAccount.getTeamId(), dormitory.getDormitoryId(), dormitory);
            teamFavoriteDormService.save(teamFavoriteDorm);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/select3")
    public ResponseEntity<?> selectDormitory3(Integer studentAccountId, Integer dormitoryId) {

        // 获取studentAccount
//        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        String studentAccountKey = "studentAccount:" + studentAccountId;
        StudentAccount studentAccount = (StudentAccount) redisTemplate.opsForValue().get(studentAccountKey);
        if (studentAccount == null) {
            studentAccount = studentAccountService.getById(studentAccountId);
            assert studentAccount != null;
            redisTemplate.opsForValue().set(studentAccountKey, studentAccount);
        }

        // 获取dormitory
//        Dormitory dormitory = dormitoryService.getById(dormitoryId);
        String dormitoryKey = "dormitory:" + dormitoryId;
        Dormitory dormitory = (Dormitory) redisTemplate.opsForValue().get(dormitoryKey);
        if (dormitory == null) {
            dormitory = dormitoryService.getById(dormitoryId);
            assert dormitory != null;
            redisTemplate.opsForValue().set(dormitoryKey, dormitory);
        }

        // 判断是否是选择宿舍阶段
        List<AllocationStage> allocationStageList = allocationStageService.list(new QueryWrapper<AllocationStage>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender())
                .eq("stage", 2));
        if (allocationStageList.isEmpty()) {
            throw new BadRequestException("select dormitory failed! (wrong time)");
        }
        Timestamp startTime = allocationStageList.get(0).getStartTime();
        Timestamp endTime = allocationStageList.get(0).getEndTime();
        Random random = new Random();  // 用于生成一个随机时间，真实开放选择时间为公布的开放时间加上此随机时间
        Timestamp nowTime = new Timestamp(System.currentTimeMillis() - random.nextInt(100, 500));
        if (nowTime.compareTo(startTime) < 0 || nowTime.compareTo(endTime) > 0) {
            throw new BadRequestException("select dormitory failed! (wrong time)");
        }


        // 判断本队是否已选宿舍
//        Team team = teamService.getById(studentAccount.getTeamId());
        String teamKey = "team:" + studentAccount.getTeamId();
        Team team = (Team) redisTemplate.opsForValue().get(teamKey);
        if (team == null) {
            team = teamService.getById(studentAccount.getTeamId());
            assert team != null;
            redisTemplate.opsForValue().set(teamKey, team);
        }
        if (team.getDormitory() != null) {
            throw new BadRequestException("select dormitory failed! (already has a dormitory)");
        }

        // 判断该宿舍是否已被选择
        List<Team> teamList = teamService.list(new QueryWrapper<Team>()
                .eq("dormitory_id", dormitory.getDormitoryId()));
        if (!teamList.isEmpty()) {
            throw new BadRequestException("select dormitory failed! (the dormitory is already selected)");
        }
        // 判断该宿舍是否能够选择
        if (allocationRelationService.list(new QueryWrapper<AllocationRelation>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender())
                .eq("dormitory_id", dormitory.getDormitoryId())).isEmpty()) {
            throw new BadRequestException("select dormitory failed! (not exists this allocation)");
        }
        // 选择宿舍
        team.setDormitoryId(dormitory.getDormitoryId());
        team.setDormitory(dormitory);
        teamService.saveOrUpdate(team);
        redisTemplate.opsForValue().set(teamKey, team);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/select")
    public ResponseEntity<?> selectDormitory4(Integer studentAccountId, Integer dormitoryId) {

        // 获取studentAccount
//        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        String studentAccountKey = "studentAccount:" + studentAccountId;
        StudentAccount studentAccount = (StudentAccount) redisTemplate.opsForValue().get(studentAccountKey);
        if (studentAccount == null) {
            studentAccount = studentAccountService.getById(studentAccountId);
            assert studentAccount != null;
            redisTemplate.opsForValue().set(studentAccountKey, studentAccount);
        }

        // 获取dormitory
//        Dormitory dormitory = dormitoryService.getById(dormitoryId);
        String dormitoryKey = "dormitory:" + dormitoryId;
        Dormitory dormitory = (Dormitory) redisTemplate.opsForValue().get(dormitoryKey);
        if (dormitory == null) {
            dormitory = dormitoryService.getById(dormitoryId);
            assert dormitory != null;
            redisTemplate.opsForValue().set(dormitoryKey, dormitory);
        }

        // 判断是否是选择宿舍阶段
        List<AllocationStage> allocationStageList = allocationStageService.list(new QueryWrapper<AllocationStage>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender())
                .eq("stage", 2));
        if (allocationStageList.isEmpty()) {
            throw new BadRequestException("select dormitory failed! (wrong time)");
        }
        Timestamp startTime = allocationStageList.get(0).getStartTime();
        Timestamp endTime = allocationStageList.get(0).getEndTime();
        Random random = new Random();  // 用于生成一个随机时间，真实开放选择时间为公布的开放时间加上此随机时间
        Timestamp nowTime = new Timestamp(System.currentTimeMillis() - random.nextInt(100, 500));
        if (nowTime.compareTo(startTime) < 0 || nowTime.compareTo(endTime) > 0) {
            throw new BadRequestException("select dormitory failed! (wrong time)");
        }


        // 判断本队是否已选宿舍
//        Team team = teamService.getById(studentAccount.getTeamId());
        String teamKey = "team:" + studentAccount.getTeamId();
        Team team = (Team) redisTemplate.opsForValue().get(teamKey);
        if (team == null) {
            team = teamService.getById(studentAccount.getTeamId());
            assert team != null;
            redisTemplate.opsForValue().set(teamKey, team);
        }
        if (team.getDormitory() != null) {
            throw new BadRequestException("select dormitory failed! (already has a dormitory)");
        }

        // 判断该宿舍是否已被选择
        List<Team> teamList = teamService.list(new QueryWrapper<Team>()
                .eq("dormitory_id", dormitory.getDormitoryId()));
        if (!teamList.isEmpty()) {
            throw new BadRequestException("select dormitory failed! (the dormitory is already selected)");
        }
        // 判断该宿舍是否能够选择
        if (allocationRelationService.list(new QueryWrapper<AllocationRelation>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender())
                .eq("dormitory_id", dormitory.getDormitoryId())).isEmpty()) {
            throw new BadRequestException("select dormitory failed! (not exists this allocation)");
        }
        // 选择宿舍
        team.setDormitoryId(dormitory.getDormitoryId());
        team.setDormitory(dormitory);
        requestSender.sendRequest(team.getTeamId() + " " + dormitoryId + " " + team.getOwnerId());
//        teamService.saveOrUpdate(team);
//        redisTemplate.opsForValue().set(teamKey, team);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getLayout")
    public List<Layout> getLayout(Integer buildingId, @RequestParam(required = false) String roomNumber) {
        List<Dormitory> dormitoryList;
        if (roomNumber != null) {
            dormitoryList = dormitoryService.list(new QueryWrapper<Dormitory>()
                    .eq("building_id", buildingId)
                    .eq("room_number", roomNumber));
        } else {
            dormitoryList = dormitoryService.list(new QueryWrapper<Dormitory>()
                    .eq("building_id", buildingId));
        }
        Set<Integer> layoutIdSet = dormitoryList.stream().map(Dormitory::getLayoutId).collect(Collectors.toSet());
        return layoutIdSet.stream().map(layoutService::getById).collect(Collectors.toList());
    }

//    @GetMapping("/getStage")
//    public AllocationStage getStage(String studentAccountId) {
//        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
//        if (studentAccount == null) {
//            throw new NotFoundException("student account not found!");
//        }
//
//        List<AllocationStage> allocationStageList = allocationStageService.list(new QueryWrapper<AllocationStage>()
//                .eq("entry_year", studentAccount.calEntryYear())
//                .eq("degree", studentAccount.calDegree())
//                .eq("gender", studentAccount.getGender()));
//        if (allocationStageList.isEmpty()) {
//            return null;
//        }
//        for (AllocationStage allocationStage : allocationStageList) {
//            if (allocationStage.getStartTime().compareTo(new Timestamp(System.currentTimeMillis())) < 0
//                    && allocationStage.getEndTime().compareTo(new Timestamp(System.currentTimeMillis())) > 0) {
//                return allocationStage;
//            }
//        }
//        return null;
//    }


    @Deprecated
    @PostMapping("/favor")
    public ResponseEntity<?> favorDormitory(@RequestBody StudentAccount studentAccount, @RequestBody Dormitory dormitory) {

        // 判断是否是收藏宿舍阶段
        List<AllocationStage> allocationStageList = allocationStageService.list(new QueryWrapper<AllocationStage>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender()));
        if (allocationStageList.isEmpty() || allocationStageList.get(0).getStage() != 1) {
            throw new BadRequestException("favor dormitory failed! (wrong stage)");
        }
        // 收藏宿舍
        List<TeamFavoriteDorm> teamFavoriteDormList = teamFavoriteDormService.list(new QueryWrapper<TeamFavoriteDorm>()
                .eq("team_id", studentAccount.getTeamId())
                .eq("dormitory_id", dormitory.getDormitoryId()));
        if (teamFavoriteDormList.isEmpty()) {
            TeamFavoriteDorm teamFavoriteDorm = new TeamFavoriteDorm(null, studentAccount.getTeamId(), dormitory.getDormitoryId(), dormitory);
            teamFavoriteDormService.save(teamFavoriteDorm);
        }
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @PostMapping("/select-d")
    public ResponseEntity<?> selectDormitoryD(@RequestBody StudentAccount studentAccount, @RequestBody Dormitory dormitory) {

        // 判断是否是选择宿舍阶段
        List<AllocationStage> allocationStageList = allocationStageService.list(new QueryWrapper<AllocationStage>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender()));
        if (allocationStageList.isEmpty() || allocationStageList.get(0).getStage() != 2) {
            throw new BadRequestException("select dormitory failed! (wrong stage)");
        }
        // 判断本队是否已选宿舍
        Team team = teamService.getById(studentAccount.getTeamId());
        if (team.getDormitory() != null) {
            throw new BadRequestException("select dormitory failed! (already has a dormitory)");
        }
        // 判断该宿舍是否已被选择
        List<Team> teamList = teamService.list(new QueryWrapper<Team>()
                .eq("dormitory_id", dormitory.getDormitoryId()));
        if (!teamList.isEmpty()) {
            throw new BadRequestException("select dormitory failed! (the dormitory is already selected)");
        }
        // 判断该宿舍是否能够选择
        if (allocationRelationService.list(new QueryWrapper<AllocationRelation>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender())
                .eq("dormitory_id", dormitory.getDormitoryId())).isEmpty()) {
            throw new BadRequestException("select dormitory failed! (not exists this allocation)");
        }
        // 选择宿舍
        team.setDormitoryId(dormitory.getDormitoryId());
        team.setDormitory(dormitory);
        teamService.saveOrUpdate(team);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/select2")
    public ResponseEntity<?> selectDormitory2(String studentAccountId, Integer dormitoryId) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        Dormitory dormitory = dormitoryService.getById(dormitoryId);
        if (studentAccount == null || dormitory == null) {
            throw new BadRequestException("student or dormitory not exist");
        }
        if (studentAccount.getTeamId() == null) {
            throw new BadRequestException("student not in a team");
        }

        // 判断是否是选择宿舍阶段
        List<AllocationStage> allocationStageList = allocationStageService.list(new QueryWrapper<AllocationStage>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender())
                .eq("stage", 2));
        if (allocationStageList.isEmpty()) {
            throw new BadRequestException("select dormitory failed! (wrong time)");
        }
        Timestamp startTime = allocationStageList.get(0).getStartTime();
        Timestamp endTime = allocationStageList.get(0).getEndTime();
        Random random = new Random();  // 用于生成一个随机时间，真实开放选择时间为公布的开放时间加上此随机时间
        Timestamp nowTime = new Timestamp(System.currentTimeMillis() - random.nextInt(100, 500));
        if (nowTime.compareTo(startTime) < 0 || nowTime.compareTo(endTime) > 0) {
            throw new BadRequestException("select dormitory failed! (wrong time)");
        }

        // 判断本队是否已选宿舍
        Team team = teamService.getById(studentAccount.getTeamId());
        if (team.getDormitory() != null) {
            throw new BadRequestException("select dormitory failed! (already has a dormitory)");
        }
        // 判断该宿舍是否已被选择
        List<Team> teamList = teamService.list(new QueryWrapper<Team>()
                .eq("dormitory_id", dormitory.getDormitoryId()));
        if (!teamList.isEmpty()) {
            throw new BadRequestException("select dormitory failed! (the dormitory is already selected)");
        }
        // 判断该宿舍是否能够选择
        if (allocationRelationService.list(new QueryWrapper<AllocationRelation>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender())
                .eq("dormitory_id", dormitory.getDormitoryId())).isEmpty()) {
            throw new BadRequestException("select dormitory failed! (not exists this allocation)");
        }
        // 选择宿舍
        team.setDormitoryId(dormitory.getDormitoryId());
        team.setDormitory(dormitory);
        teamService.saveOrUpdate(team);
        return ResponseEntity.ok().build();
    }


    @Deprecated
    @PostMapping("/select-test")
    public ResponseEntity<?> selectDormitory(Integer studentAccountId, Integer dormitoryId) {
//        System.out.println(studentAccountId + " " + dormitoryId);
//        Team team = new Team(dormitoryId, dormitoryId, String.valueOf(studentAccountId), null);
//        requestSender.sendRequest(team.getTeamId() + " " + team.getDormitoryId() + " " + team.getOwnerId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getStage")
    public Integer getStage(String studentAccountId){
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        if (studentAccount == null) {
            throw new NotFoundException("student account not found!");
        }
        List<AllocationStage> allocationStageList = allocationStageService.list(new QueryWrapper<AllocationStage>()
                .eq("entry_year", studentAccount.calEntryYear())
                .eq("degree", studentAccount.calDegree())
                .eq("gender", studentAccount.getGender()));
        Integer stage = 0;
        for (AllocationStage allocationStage : allocationStageList) {
            if (allocationStage.getStartTime().compareTo(new Timestamp(System.currentTimeMillis())) < 0
                    && allocationStage.getEndTime().compareTo(new Timestamp(System.currentTimeMillis())) > 0) {
               if (allocationStage.getStage() > stage) {
                   stage = allocationStage.getStage();
               }
            }
        }
        return stage;
    }
}
