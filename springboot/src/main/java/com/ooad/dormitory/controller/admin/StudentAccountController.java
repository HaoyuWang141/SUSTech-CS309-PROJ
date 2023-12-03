package com.ooad.dormitory.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.service.StudentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/studentAccount")
@CrossOrigin
public class StudentAccountController {

    private final StudentAccountService studentAccountService;

    @Autowired
    public StudentAccountController(StudentAccountService studentAccountService) {
        this.studentAccountService = studentAccountService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStudentAccount(@RequestBody List<StudentAccount> studentAccountList) {
        record Response(int successCount, List<StudentAccount> failedStudentList) {
        }

        int successCount = 0;
        List<StudentAccount> failedStudentList = new ArrayList<>();
        for (StudentAccount studentAccount : studentAccountList) {
            try {
                if (studentAccountService.save(studentAccount)) {
                    successCount++;
                }
            } catch (Exception e) {
                failedStudentList.add(studentAccount);
            }
        }
        System.out.println(failedStudentList);
        return ResponseEntity.ok(new Response(successCount, failedStudentList));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStudentAccount(@RequestBody List<String> studentIdList) {
        record Response(int successCount, List<String> failedIdList) {
        }
        int successCount = 0;
        List<String> failedIdList = new ArrayList<>();

        for (String studentId : studentIdList) {
            try {
                if (studentAccountService.removeById(studentId)) {
                    successCount++;
                }
            } catch (Exception e) {
                failedIdList.add(studentId);
            }
        }

        return ResponseEntity.ok(new Response(successCount, failedIdList));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateStudentAccount(@RequestBody List<StudentAccount> studentAccountList) {
        record Response(int successCount, List<String> failedIdList) {
        }

        int successCount = 0;
        List<String> failedIdList = new ArrayList<>();

        for (StudentAccount studentAccount : studentAccountList) {
            try {
                if (studentAccountService.updateById(studentAccount)) {
                    successCount++;
                }
            } catch (Exception e) {
                failedIdList.add(studentAccount.getStudentId());
            }
        }

        return ResponseEntity.ok(new Response(successCount, failedIdList));
    }

    @GetMapping("/get")
    public List<StudentAccount> getStudentAccount(@RequestParam(required = false) Integer entryYear, @RequestParam(required = false) String degree) {
        QueryWrapper<StudentAccount> queryWrapper = new QueryWrapper<>();
        if ((entryYear == null && (degree == null || degree.isEmpty()))) {
            System.out.println("entryYear and degree is null");
        } else if (entryYear == null) {
            if (degree.equals("master")) {
                queryWrapper.like("student_id", "________");
            } else if (degree.equals("doctor")) {
                queryWrapper.like("student_id", "________");
            } else {
                throw new RuntimeException("degree is Not doctor or master");
            }
        } else if (degree == null) {
            if (entryYear > 100) {
                entryYear = entryYear % 100;
            }
            queryWrapper.like("student_id", "_" + entryYear + "_____");
        } else {
            if (entryYear > 100) {
                entryYear = entryYear % 100;
            }
            if (degree.equals("master")) {
                queryWrapper.like("student_id", "_" + entryYear + "_____");
            } else if (degree.equals("doctor")) {
                queryWrapper.like("student_id", "_" + entryYear + "_____");
            } else {
                throw new RuntimeException("degree is Not doctor or master");
            }
        }

        return studentAccountService.list(queryWrapper);
    }
}
