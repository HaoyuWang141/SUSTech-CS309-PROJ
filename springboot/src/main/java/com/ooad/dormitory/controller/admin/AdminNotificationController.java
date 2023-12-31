package com.ooad.dormitory.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.AdminAccount;
import com.ooad.dormitory.entity.Notification;
import com.ooad.dormitory.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/admin/notification")
@CrossOrigin
public class AdminNotificationController {

    private final NotificationService notificationService;

    @Autowired
    public AdminNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publish(Integer adminAccountId,
                                     @RequestParam(required = false) Integer entryYear,
                                     @RequestParam(required = false) Integer degree,
                                     @RequestParam(required = false) Integer gender,
                                     String title, String content) {
        Notification notification = new Notification(null, adminAccountId, entryYear, degree, gender, title, content, new Timestamp(System.currentTimeMillis()));
        notificationService.save(notification);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public List<Notification> getNotifications(Integer adminAccountId) {
        return notificationService.list(new QueryWrapper<Notification>().eq("publisher_id", adminAccountId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(Integer notificationId) {
        notificationService.removeById(notificationId);
        return ResponseEntity.ok().build();
    }
}
