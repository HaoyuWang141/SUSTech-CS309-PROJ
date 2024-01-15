package com.ooad.dormitory.controller.student;

import com.ooad.dormitory.entity.Authentication;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.exception.BackEndException;
import com.ooad.dormitory.interceptor.TokenUtils;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.mapper.StudentAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class LoginController {

    private final AuthenticationMapper authenticationMapper;
    private final StudentAccountMapper studentAccountMapper;

    private final Integer failureTimeInMinutes = 20;

    @Autowired
    public LoginController(AuthenticationMapper authenticationMapper, StudentAccountMapper studentAccountMapper) {
        this.authenticationMapper = authenticationMapper;
        this.studentAccountMapper = studentAccountMapper;
    }

//    @PostMapping("/login")
//        public ResponseEntity<?> login(String studentId, String password) {
//        System.out.println(studentId + " " + password);
//        Authentication authentication = authenticationMapper.selectById(studentId);
//        if (authentication != null && Objects.equals(authentication.getStudentPassword(), password) && authentication.getOnlineAmount() < 1) {
//            if (authentication.getToken()!=null && new Time(System.currentTimeMillis()).compareTo(authentication.getTokenFailureTime()) >= 0) {
//                return ResponseEntity.ok(authentication.getToken());
//            }
//
//            String token = TokenUtils.generateToken(studentId);
//
////            String token = createToken(studentId);
////            authentication.setToken(token);
////            authentication.setTokenFailureTime(new Time(new Time(System.currentTimeMillis()).getTime() + (failureTimeInMinutes * 60 * 1000)));
////            authentication.setOnlineAmount(authentication.getOnlineAmount() + 1);
//            authenticationMapper.updateById(authentication);
//            return ResponseEntity.ok(token);
//        }
//        else {
//            throw new BackEndException("login failed!");
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(String studentId, String password) {
        System.out.println(studentId + " " + password);
        Authentication authentication = authenticationMapper.selectById(studentId);
        if (authentication != null && Objects.equals(authentication.getStudentPassword(), password)) {
            if (!TokenUtils.checkOnlineAmount(studentId)) {
                throw new BackEndException("online amount exceeded!");
            }
            String token = TokenUtils.generateToken(studentId);
            return ResponseEntity.ok(token);
        }
        else {
            throw new BackEndException("login failed! " + authentication.getStudentPassword());
        }
    }

    @GetMapping("/getSelf")
    public StudentAccount getSelf(@RequestBody String studentId) {
        return studentAccountMapper.selectById(studentId);
    }


    private synchronized static String createToken(String studentId) {
        Random random = new Random();
        return studentId + "-"
                + System.currentTimeMillis() + "-"
                + String.format("%04x", random.nextInt(65536));
    }

    public static boolean checkAuthentication(AuthenticationMapper authenticationMapper, String token) {
        try {
            Authentication authentication = authenticationMapper.selectByToken(token);
            if (authentication != null && new Time(System.currentTimeMillis()).compareTo(authentication.getTokenFailureTime()) < 0) {
                return true;
            }
            else {
//            throw new RuntimeException("invalid token!");
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("authenticate failed!\n" + e.getMessage());
        }
    }

    public static boolean checkAuthentication2(RedisTemplate<String, Object> redisTemplate, AuthenticationMapper authenticationMapper, String token) {
        // 处理高并发
        try {
//            Authentication authentication = authenticationMapper.selectByToken(token);
            String authenticationKey = "authentication:" + token;
            Authentication authentication = (Authentication) redisTemplate.opsForValue().get(authenticationKey);
            if (authentication == null) {
                authentication = authenticationMapper.selectByToken(token);
                if (authentication != null) {
                    redisTemplate.opsForValue().set(authenticationKey, authentication);
                }
            }

            if (authentication != null && new Time(System.currentTimeMillis()).compareTo(authentication.getTokenFailureTime()) < 0) {
                return true;
            }
            else {
//            throw new RuntimeException("invalid token!");
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("authenticate failed!\n" + e.getMessage());
        }
    }

}
