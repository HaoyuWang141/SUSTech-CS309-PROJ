package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Notification;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.mapper.NotificationMapper;
import com.ooad.dormitory.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student/notification")
@CrossOrigin
public class NotificationController {

    private final NotificationService notificationService;
    private final AuthenticationMapper authenticationMapper;

    @Autowired
    public NotificationController(NotificationService notificationService, AuthenticationMapper authenticationMapper) {
        this.notificationService = notificationService;
        this.authenticationMapper = authenticationMapper;
    }

    @GetMapping("/get")
    public List<Notification> getNotifications(@RequestBody StudentAccount studentAccount, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return notificationService.list().stream()
                .filter(notification -> notification.getEntryYear() == null || notification.getEntryYear().equals(studentAccount.calEntryYear()))
                .filter(notification -> notification.getDegree() == null || notification.getDegree().equals(studentAccount.calDegree()))
                .filter(notification -> notification.getGender() == null || notification.getGender().equals(studentAccount.getGender()))
                .collect(Collectors.toList());
    }
}
