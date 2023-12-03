package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Notification;
import com.ooad.dormitory.entity.StudentAccount;
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

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/get")
    public List<Notification> getNotifications(@RequestBody StudentAccount studentAccount) {
        return notificationService.list().stream()
                .filter(notification -> notification.getEntryYear() == null || notification.getEntryYear().equals(studentAccount.calEntryYear()))
                .filter(notification -> notification.getDegree() == null || notification.getDegree().equals(studentAccount.calDegree()))
                .filter(notification -> notification.getGender() == null || notification.getGender().equals(studentAccount.getGender()))
                .collect(Collectors.toList());
    }
}
