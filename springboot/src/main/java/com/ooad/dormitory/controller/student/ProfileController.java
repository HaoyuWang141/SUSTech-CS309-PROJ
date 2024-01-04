package com.ooad.dormitory.controller.student;

import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.exception.BackEndException;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.mapper.StudentAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;

@RestController
@RequestMapping("/student/profile")
@CrossOrigin
public class ProfileController {

    private final StudentAccountMapper studentAccountMapper;
    private final AuthenticationMapper authenticationMapper;

    @Autowired
    public ProfileController(StudentAccountMapper studentAccountMapper, AuthenticationMapper authenticationMapper) {
        this.studentAccountMapper = studentAccountMapper;
        this.authenticationMapper = authenticationMapper;
    }

    @PostMapping("/set2")
    public ResponseEntity<?> setProfile2(String studentAccountId,
                                      @RequestParam(required = false) String photoUrl,
                                      @RequestParam(required = false) String sleepTimeString,
                                      @RequestParam(required = false) String wakeUpTimeString,
                                      @RequestParam(required = false) Integer airConditionerTemperature,
                                      @RequestParam(required = false) Boolean snore,
                                      @RequestParam(required = false) String qq,
                                      @RequestParam(required = false) String email,
                                      @RequestParam(required = false) String wechat) {
        try {
            StudentAccount studentAccount = studentAccountMapper.selectById(studentAccountId);
            assert studentAccount != null;

            if (photoUrl != null) {
                studentAccount.setPhotoUrl(photoUrl);
            }
            if (sleepTimeString != null) {
                Time sleepTime = Time.valueOf(sleepTimeString + ":00");
                studentAccount.setSleepTime(sleepTime);
            }
            if (wakeUpTimeString != null) {
                Time wakeUpTime = Time.valueOf(wakeUpTimeString + ":00");
                studentAccount.setWakeUpTime(wakeUpTime);
            }
            if (airConditionerTemperature != null) {
                studentAccount.setAirConditionerTemperature(airConditionerTemperature);
            }
            if (snore != null) {
                studentAccount.setSnore(snore);
            }
            if (qq != null) {
                studentAccount.setQq(qq);
            }
            if (email != null) {
                studentAccount.setEmail(email);
            }
            if (wechat != null) {
                studentAccount.setWechat(wechat);
            }
            studentAccountMapper.updateById(studentAccount);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new BackEndException("set profile failed!\n" + e.getMessage());
        }
    }


    @Deprecated
    @PostMapping("/set")
    public void setProfile(@RequestBody StudentAccount studentAccount,
                           @RequestParam(required = false) String photoUrl,
                           @RequestParam(required = false) Time sleepTime,
                           @RequestParam(required = false) Time wakeUpTime,
                           @RequestParam(required = false) Integer airConditionerTemperature,
                           @RequestParam(required = false) Boolean snore,
                           @RequestParam(required = false) String qq,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String wechat) {
        if (photoUrl != null) {
            studentAccount.setPhotoUrl(photoUrl);
        }
        if (sleepTime != null) {
            studentAccount.setSleepTime(sleepTime);
        }
        if (wakeUpTime != null) {
            studentAccount.setWakeUpTime(wakeUpTime);
        }
        if (airConditionerTemperature != null) {
            studentAccount.setAirConditionerTemperature(airConditionerTemperature);
        }
        if (snore != null) {
            studentAccount.setSnore(snore);
        }
        if (qq != null) {
            studentAccount.setQq(qq);
        }
        if (email != null) {
            studentAccount.setEmail(email);
        }
        if (wechat != null) {
            studentAccount.setWechat(wechat);
        }
        studentAccountMapper.updateById(studentAccount);
    }
}
