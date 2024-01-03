package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Invitation;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.entity.Team;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.service.InvitationService;
import com.ooad.dormitory.service.StudentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student/team")
@CrossOrigin
public class TeamController {

    private final StudentAccountService studentAccountService;
    private final InvitationService invitationService;
    private final AuthenticationMapper authenticationMapper;

    @Autowired
    public TeamController(StudentAccountService studentAccountService, InvitationService invitationService, AuthenticationMapper authenticationMapper) {
        this.studentAccountService = studentAccountService;
        this.invitationService = invitationService;
        this.authenticationMapper = authenticationMapper;
    }

    @GetMapping("/getTeam2")
    public List<StudentAccount> getTeam2(String studentAccountId, @RequestBody String token) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        assert studentAccount != null;

        LoginController.checkAuthentication(authenticationMapper, token);
        return studentAccountService.list(new QueryWrapper<StudentAccount>().eq("teamId", studentAccount.getTeamId()));
    }

    @PostMapping("/quitTeam2")
    public ResponseEntity<?> quitTeam2(@RequestBody String studentAccountId, @RequestBody String token) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        assert studentAccount != null;

        LoginController.checkAuthentication(authenticationMapper, token);
        List<StudentAccount> studentAccountList = studentAccountService.list(new QueryWrapper<StudentAccount>()
                .eq("teamId", studentAccount.getTeamId()));
        if (studentAccountList.size() > 2) {
            studentAccount.setTeamId(null);
            studentAccount.setTeam(null);
            studentAccountService.saveOrUpdate(studentAccount);
        }
        else if (studentAccountList.size() == 2) {
            for (StudentAccount each : studentAccountList) {
                each.setTeamId(null);
                each.setTeam(null);
                studentAccountService.saveOrUpdate(each);
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getInvitations2")
    public List<Invitation> getInvitations2(String studentAccountId, @RequestBody String token) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        assert studentAccount != null;

        LoginController.checkAuthentication(authenticationMapper, token);
        return invitationService.list(new QueryWrapper<Invitation>().eq("inviteeId", studentAccount.getTeamId()));
    }

    @PostMapping("/invite2")
    public ResponseEntity<?> invite2(@RequestBody String inviterId, @RequestBody String inviteeId, @RequestBody String token) {
        StudentAccount inviter = studentAccountService.getById(inviterId);
        StudentAccount invitee = studentAccountService.getById(inviteeId);
        assert inviter != null && invitee != null;

        LoginController.checkAuthentication(authenticationMapper, token);
        if (!Objects.equals(inviter.calEntryYear(), invitee.calEntryYear())
                || !Objects.equals(inviter.calDegree(), invitee.calDegree())
                || !Objects.equals(inviter.getGender(), invitee.getGender())
                || Objects.equals(inviter.getStudentId(), invitee.getStudentId())) {
            throw new RuntimeException("invite failed!");
        }
        Invitation invitation = new Invitation(null, inviter.getStudentId(), invitee.getStudentId(), new Timestamp(System.currentTimeMillis()), inviter, invitee);
        invitationService.save(invitation);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept2")
    public ResponseEntity<?> accept2(@RequestBody Integer invitationId, @RequestBody String token) {
        Invitation invitation = invitationService.getById(invitationId);
        assert invitation != null;

        LoginController.checkAuthentication(authenticationMapper, token);
        StudentAccount inviter = invitation.getInviter();
        StudentAccount invitee = invitation.getInvitee();
        if (invitee.getTeam() != null) {
            throw new RuntimeException("accept invitation failed! (invitee already has a team)");
        }
        if (inviter.getTeam() == null) {
            inviter.setTeam(new Team());
            inviter.setTeamId(inviter.getTeam().getTeamId());
            studentAccountService.saveOrUpdate(inviter);
        }
        invitee.setTeamId(inviter.getTeamId());
        invitee.setTeam(inviter.getTeam());
        studentAccountService.saveOrUpdate(invitee);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getStudents")
    public List<StudentAccount> getStudents(@RequestParam(required = false) String studentId,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Time sleepTimeLeftBound,
                                            @RequestParam(required = false) Time sleepTimeRightBound,
                                            @RequestParam(required = false) Time wakeUpTimeLeftBound,
                                            @RequestParam(required = false) Time wakeUpTimeRightBound,
                                            @RequestParam(required = false) Integer airConditionerTemperatureLeftBound,
                                            @RequestParam(required = false) Integer airConditionerTemperatureRightBound,
                                            @RequestParam(required = false) Boolean snore,
                                            @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return studentAccountService.list().stream()
                .filter(studentAccount -> studentId == null || Objects.equals(studentAccount.getStudentId(), studentId))
                .filter(studentAccount -> name == null || Objects.equals(studentAccount.getName(), name))
                .filter(studentAccount -> sleepTimeLeftBound == null || studentAccount.getSleepTime().compareTo(sleepTimeLeftBound) >= 0)
                .filter(studentAccount -> sleepTimeRightBound == null || studentAccount.getSleepTime().compareTo(sleepTimeRightBound) <= 0)
                .filter(studentAccount -> wakeUpTimeLeftBound == null || studentAccount.getWakeUpTime().compareTo(wakeUpTimeLeftBound) >= 0)
                .filter(studentAccount -> wakeUpTimeRightBound == null || studentAccount.getWakeUpTime().compareTo(wakeUpTimeRightBound) <= 0)
                .filter(studentAccount -> airConditionerTemperatureLeftBound == null || studentAccount.getAirConditionerTemperature() >= airConditionerTemperatureLeftBound)
                .filter(studentAccount -> airConditionerTemperatureRightBound == null || studentAccount.getAirConditionerTemperature() <= airConditionerTemperatureRightBound)
                .filter(studentAccount -> snore == null || studentAccount.getSnore() == snore)
                .collect(Collectors.toList());
    }

    @GetMapping("/getRecommendation2")
    public List<StudentAccount> getRecommendation2(String studentAccountId, @RequestBody String token) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        assert studentAccount != null;

        LoginController.checkAuthentication(authenticationMapper, token);
        return studentAccountService.list().stream()
                .filter(s -> s.getTeamId() == null && !Objects.equals(studentAccount.getStudentId(), s.getStudentId()))
                .sorted(Comparator.comparingDouble(s -> studentAccount.getSimilarity((StudentAccount) s)).reversed())
                .collect(Collectors.toList());
    }

    @GetMapping("getStudent")
    public StudentAccount getStudent(String studentId, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return studentAccountService.getById(studentId);
    }


    @Deprecated
    @GetMapping("/getTeam")
    public List<StudentAccount> getTeam(@RequestBody StudentAccount studentAccount, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return studentAccountService.list(new QueryWrapper<StudentAccount>().eq("teamId", studentAccount.getTeamId()));
    }

    @Deprecated
    @PostMapping("/quitTeam")
    public ResponseEntity<?> quitTeam(@RequestBody StudentAccount studentAccount, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        List<StudentAccount> studentAccountList = studentAccountService.list(new QueryWrapper<StudentAccount>()
                .eq("teamId", studentAccount.getTeamId()));
        if (studentAccountList.size() > 2) {
            studentAccount.setTeamId(null);
            studentAccount.setTeam(null);
            studentAccountService.saveOrUpdate(studentAccount);
        }
        else if (studentAccountList.size() == 2) {
            for (StudentAccount each : studentAccountList) {
                each.setTeamId(null);
                each.setTeam(null);
                studentAccountService.saveOrUpdate(each);
            }
        }
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @GetMapping("/getInvitations")
    public List<Invitation> getInvitations(@RequestBody StudentAccount studentAccount, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return invitationService.list(new QueryWrapper<Invitation>().eq("inviteeId", studentAccount.getTeamId()));
    }

    @Deprecated
    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestBody StudentAccount inviter, @RequestBody StudentAccount invitee, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        if (!Objects.equals(inviter.calEntryYear(), invitee.calEntryYear())
                || !Objects.equals(inviter.calDegree(), invitee.calDegree())
                || !Objects.equals(inviter.getGender(), invitee.getGender())
                || Objects.equals(inviter.getStudentId(), invitee.getStudentId())) {
            throw new RuntimeException("invite failed!");
        }
        Invitation invitation = new Invitation(null, inviter.getStudentId(), invitee.getStudentId(), new Timestamp(System.currentTimeMillis()), inviter, invitee);
        invitationService.save(invitation);
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @PostMapping("/accept")
    public ResponseEntity<?> accept(@RequestBody Invitation invitation, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        StudentAccount inviter = invitation.getInviter();
        StudentAccount invitee = invitation.getInvitee();
        if (invitee.getTeam() != null) {
            throw new RuntimeException("accept invitation failed! (invitee already has a team)");
        }
        if (inviter.getTeam() == null) {
            inviter.setTeam(new Team());
            inviter.setTeamId(inviter.getTeam().getTeamId());
            studentAccountService.saveOrUpdate(inviter);
        }
        invitee.setTeamId(inviter.getTeamId());
        invitee.setTeam(inviter.getTeam());
        studentAccountService.saveOrUpdate(invitee);
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @GetMapping("/getRecommendation")
    public List<StudentAccount> getRecommendation(@RequestBody StudentAccount studentAccount, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return studentAccountService.list().stream()
                .filter(s -> s.getTeamId() == null && !Objects.equals(studentAccount.getStudentId(), s.getStudentId()))
                .sorted(Comparator.comparingDouble(s -> studentAccount.getSimilarity((StudentAccount) s)).reversed())
                .collect(Collectors.toList());
    }



}
