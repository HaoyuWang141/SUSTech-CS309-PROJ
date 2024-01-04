package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Invitation;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.entity.Team;
import com.ooad.dormitory.exception.BackEndException;
import com.ooad.dormitory.exception.BadRequestException;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.service.InvitationService;
import com.ooad.dormitory.service.StudentAccountService;
import com.ooad.dormitory.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
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
    private TeamService teamService;

    @Autowired
    public TeamController(StudentAccountService studentAccountService, InvitationService invitationService, AuthenticationMapper authenticationMapper) {
        this.studentAccountService = studentAccountService;
        this.invitationService = invitationService;
        this.authenticationMapper = authenticationMapper;
    }

    @GetMapping("/getTeam2")
    public List<StudentAccount> getTeam2(String studentAccountId) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        assert studentAccount != null;
        return studentAccountService.list(new QueryWrapper<StudentAccount>().eq("team_id", studentAccount.getTeamId()));
    }

    @PostMapping("/quitTeam2")
    public ResponseEntity<?> quitTeam2(@RequestBody String studentAccountId) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        assert studentAccount != null;

        List<StudentAccount> studentAccountList = studentAccountService.list(new QueryWrapper<StudentAccount>()
                .eq("team_id", studentAccount.getTeamId()));
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
    public List<Invitation> getInvitations2(String studentAccountId) {
        try {
            StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
            assert studentAccount != null;
            return invitationService.list(new QueryWrapper<Invitation>().eq("invitee_id", studentAccount.getStudentId()));
        } catch (Exception e) {
            throw new BackEndException("get invitations failed!\n" + e.getMessage());
        }
    }

    @PostMapping("/invite2")
    public ResponseEntity<?> invite2(String inviterId, String inviteeId) {
        StudentAccount inviter = studentAccountService.getById(inviterId);
        StudentAccount invitee = studentAccountService.getById(inviteeId);
        if( inviter == null || invitee == null) {
            throw new RuntimeException("inviter or invitee not found!");
        }

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
    public ResponseEntity<?> accept2(Integer invitationId) {
        try {
            Invitation invitation = invitationService.getById(invitationId);
            if (invitation == null) {
                throw new BadRequestException("invitation not found!");
            }

            StudentAccount inviter = studentAccountService.getById(invitation.getInviterId());
            StudentAccount invitee = studentAccountService.getById(invitation.getInviteeId());

            inviter.setTeam(teamService.getById(inviter.getTeamId()));
            invitee.setTeam(teamService.getById(invitee.getTeamId()));

            if (inviter.getTeam() == null) {
                Team team = new Team();
                team.setOwnerId(inviter.getStudentId());
                teamService.save(team);
                inviter.setTeam(team);
                inviter.setTeamId(team.getTeamId());
                studentAccountService.saveOrUpdate(inviter);
            }
            invitee.setTeamId(inviter.getTeamId());
            studentAccountService.saveOrUpdate(invitee);

            List<Integer> invitationIdList = invitationService.list(new QueryWrapper<Invitation>().eq("invitee_id", invitee.getStudentId())).stream().map(Invitation::getId).toList();
            invitationService.removeByIds(invitationIdList);
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BackEndException("accept invitation failed!\n" + e.getMessage());
        }
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
                                            @RequestParam(required = false) Boolean snore) {
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
    public List<StudentAccount> getRecommendation2(String studentAccountId) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        assert studentAccount != null;

        return studentAccountService.list().stream()
                .filter(s -> s.getTeamId() == null
                        && !Objects.equals(studentAccount.getStudentId(), s.getStudentId())
                        && Objects.equals(studentAccount.getGender(), s.getGender())
                        && Objects.equals(studentAccount.calEntryYear(), s.calEntryYear())
                        && Objects.equals(studentAccount.calDegree(), s.calDegree()))
                .sorted(Comparator.comparingDouble(studentAccount::calSimilarity).reversed())
                .collect(Collectors.toList());
    }

    @GetMapping("getStudent")
    public StudentAccount getStudent(String studentId ) {
         
        return studentAccountService.getById(studentId);
    }


    @Deprecated
    @GetMapping("/getTeam")
    public List<StudentAccount> getTeam(@RequestBody StudentAccount studentAccount ) {
         
        return studentAccountService.list(new QueryWrapper<StudentAccount>().eq("team_id", studentAccount.getTeamId()));
    }

    @Deprecated
    @PostMapping("/quitTeam")
    public ResponseEntity<?> quitTeam(@RequestBody StudentAccount studentAccount ) {
         
        List<StudentAccount> studentAccountList = studentAccountService.list(new QueryWrapper<StudentAccount>()
                .eq("team_id", studentAccount.getTeamId()));
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
    public List<Invitation> getInvitations(@RequestBody StudentAccount studentAccount ) {
        return invitationService.list(new QueryWrapper<Invitation>().eq("invitee_id", studentAccount.getStudentId()));
    }

    @Deprecated
    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestBody StudentAccount inviter, @RequestBody StudentAccount invitee ) {
         
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
    public ResponseEntity<?> accept(@RequestBody Invitation invitation ) {
         
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
    public List<StudentAccount> getRecommendation(@RequestBody StudentAccount studentAccount ) {
         
        return studentAccountService.list().stream()
                .filter(s -> s.getTeamId() == null && !Objects.equals(studentAccount.getStudentId(), s.getStudentId()))
                .sorted(Comparator.comparingDouble(s -> studentAccount.calSimilarity((StudentAccount) s)).reversed())
                .collect(Collectors.toList());
    }
}
