package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Invitation;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.entity.Team;
import com.ooad.dormitory.service.InvitationService;
import com.ooad.dormitory.service.StudentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/student/team")
@CrossOrigin
public class TeamController {

    private final StudentAccountService studentAccountService;
    private final InvitationService invitationService;

    @Autowired
    public TeamController(StudentAccountService studentAccountService,
                          InvitationService invitationService) {
        this.studentAccountService = studentAccountService;
        this.invitationService = invitationService;
    }

    @GetMapping("/getTeam")
    public List<StudentAccount> getTeam(StudentAccount studentAccount) {
       return studentAccountService.list(new QueryWrapper<StudentAccount>().eq("teamId", studentAccount.getTeamId()));
    }

    @PostMapping("/quitTeam")
    public ResponseEntity<?> quitTeam(StudentAccount studentAccount) {
        List<StudentAccount> studentAccountList = studentAccountService.list(new QueryWrapper<StudentAccount>().eq("teamId", studentAccount.getTeamId()));
        // TODO
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getInvitations")
    public List<Invitation> getInvitations(StudentAccount studentAccount) {
        return invitationService.list(new QueryWrapper<Invitation>().eq("inviteeId", studentAccount.getTeamId()));
    }

    @PostMapping("/invite")
    public ResponseEntity<?> invite(StudentAccount inviter, StudentAccount invitee) {
        Invitation invitation = new Invitation(null, inviter.getStudentId(), invitee.getStudentId(), new Timestamp(System.currentTimeMillis()), inviter, invitee);
        invitationService.save(invitation);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    public ResponseEntity<?> accept(Invitation invitation) {  // 若受邀者已在队伍中会抛出异常
        StudentAccount inviter = invitation.getInviter();
        StudentAccount invitee = invitation.getInvitee();
        if (invitee.getTeam() != null) {
            throw new RuntimeException("accept invitation failed!");
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
}
