package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Notification;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.exception.BackEndException;
import com.ooad.dormitory.exception.UnauthorizedException;
import com.ooad.dormitory.interceptor.TokenUtils;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.mapper.NotificationMapper;
import com.ooad.dormitory.service.NotificationService;
import com.ooad.dormitory.service.StudentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student/notification")
@CrossOrigin
public class NotificationController {

    private final StudentAccountService studentAccountService;
    private final NotificationService notificationService;
    private final AuthenticationMapper authenticationMapper;

    @Autowired
    public NotificationController(StudentAccountService studentAccountService, NotificationService notificationService, AuthenticationMapper authenticationMapper) {
        this.studentAccountService = studentAccountService;
        this.notificationService = notificationService;
        this.authenticationMapper = authenticationMapper;
    }

    @GetMapping("/get2")
    public List<Notification> getNotifications2(String studentAccountId) {
        try {
            StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
            assert studentAccount != null;

            return notificationService.list().stream()
                    .filter(notification -> notification.getEntryYear() == null || notification.getEntryYear().equals(studentAccount.calEntryYear()))
                    .filter(notification -> notification.getDegree() == null || notification.getDegree().equals(studentAccount.calDegree()))
                    .filter(notification -> notification.getGender() == null || notification.getGender().equals(studentAccount.getGender()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BackEndException("get notifications failed!\n" + e.getMessage());
        }
    }

    @GetMapping("/get2WithAuth")
    public List<Notification> getNotificationsWithAuth(@RequestHeader("Authorization") String token, String studentAccountId) {
        if (!TokenUtils.validateToken(token)) {
            throw new UnauthorizedException("token invalid!");
        }
        try {
            StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
            assert studentAccount != null;

            return notificationService.list().stream()
                    .filter(notification -> notification.getEntryYear() == null || notification.getEntryYear().equals(studentAccount.calEntryYear()))
                    .filter(notification -> notification.getDegree() == null || notification.getDegree().equals(studentAccount.calDegree()))
                    .filter(notification -> notification.getGender() == null || notification.getGender().equals(studentAccount.getGender()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BackEndException("get notifications failed!\n" + e.getMessage());
        }
    }


    @Deprecated
    @GetMapping("/get")
    public List<Notification> getNotifications(@RequestBody StudentAccount studentAccount) {
        return notificationService.list().stream()
                .filter(notification -> notification.getEntryYear() == null || notification.getEntryYear().equals(studentAccount.calEntryYear()))
                .filter(notification -> notification.getDegree() == null || notification.getDegree().equals(studentAccount.calDegree()))
                .filter(notification -> notification.getGender() == null || notification.getGender().equals(studentAccount.getGender()))
                .collect(Collectors.toList());
    }
}
