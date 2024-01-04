package com.ooad.dormitory.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Authentication;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.service.StudentAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/studentAccount")
@CrossOrigin
public class StudentAccountController {

    private final StudentAccountService studentAccountService;
    private final AuthenticationMapper authenticationMapper;

    @Autowired
    public StudentAccountController(StudentAccountService studentAccountService, AuthenticationMapper authenticationMapper) {
        this.studentAccountService = studentAccountService;
        this.authenticationMapper = authenticationMapper;
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
                    Authentication authentication = new Authentication();
                    authentication.setStudentId(studentAccount.getStudentId());
                    authentication.setStudentPassword("123456");
                    authentication.setOnlineAmount(0);
                    authenticationMapper.insert(authentication);
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
    @Operation(summary = "delete student account", description = "通过学号列表删除学生账号")
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
    @Operation(
            summary = "get student account",
            description = """
                    entryYear: 入学年份 (如 2019, 2020)
                    degree: 学位 (如 undergraduate, master, doctor)
                    gender: 0:女, 1:男
                    三个参数均可为空, 为空时表示不限制
                    """
    )
    public List<StudentAccount> getStudentAccount(@RequestParam @Schema(example = "2019") Integer entryYear,
                                                  @RequestParam @Schema(description = "可选: undergraduate, master, doctor") String degree,
                                                  @RequestParam @Schema(description = "0:女, 1:男") Integer gender) {
        QueryWrapper<StudentAccount> queryWrapper = new QueryWrapper<>();
        String entryYearShort = entryYear == null ? "__" : String.valueOf(entryYear % 100);
        if (degree == null) {
            degree = "_";
        } else if (degree.equals("undergraduate")) {
            degree = "1";
        } else if (degree.equals("master")) {
            degree = "2";
        } else if (degree.equals("doctor")) {
            degree = "3";
        } else {
            throw new RuntimeException("Degree is invalid");
        }
        queryWrapper.like("student_id", "1" + entryYearShort + degree + "____");
        if (gender != null) {
            if (gender == 1) {
                queryWrapper.eq("gender", 1);
            } else if (gender == 0) {
                queryWrapper.eq("gender", 0);
            }
        }
        return studentAccountService.list(queryWrapper);
    }

    @GetMapping("/getStudent")
    public StudentAccount getStudent(String studentId) {
        return studentAccountService.getById(studentId);
    }

    @GetMapping("/getStudents")
    public List<StudentAccount> getStudents(String partStudentId) {
        return studentAccountService.list().stream()
                .filter(studentAccount -> studentAccount.getStudentId().contains(partStudentId))
                .collect(Collectors.toList());
    }


}
