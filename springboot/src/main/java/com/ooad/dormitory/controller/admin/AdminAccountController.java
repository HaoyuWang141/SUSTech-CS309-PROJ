package com.ooad.dormitory.controller.admin;

import com.ooad.dormitory.entity.AdminAccount;
import com.ooad.dormitory.mapper.AdminAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/adminAccount")
@CrossOrigin
public class AdminAccountController {

    private final AdminAccountMapper adminAccountMapper;

    @Autowired
    public AdminAccountController(AdminAccountMapper adminAccountMapper) {
        this.adminAccountMapper = adminAccountMapper;
    }

    @PostMapping("/create")
    public void createAdminAccount(String accountName, String password) {
        try {
            AdminAccount adminAccount = new AdminAccount(null, accountName, password);
            adminAccountMapper.insert(adminAccount);
        } catch (Exception e) {
            throw new RuntimeException("insert admin account failed!");
        }
    }

    @DeleteMapping("/delete")
    public void deleteAdminAccount(Integer accountId) {
        try {
            adminAccountMapper.deleteById(accountId);
        } catch (Exception e) {
            throw new RuntimeException("delete admin account failed!");
        }
    }

    @PutMapping("/update")
    public void updateAdminAccount(Integer accountId, String accountName, String password) {
        try {
            AdminAccount adminAccount = new AdminAccount(accountId, accountName, password);
            adminAccountMapper.updateById(adminAccount);
        } catch (Exception e) {
            throw new RuntimeException("update admin account failed!");
        }
    }

    @GetMapping("/get")
    public List<AdminAccount> getAdminAccount() {
        try {
            return adminAccountMapper.selectList(null);
        } catch (Exception e) {
            throw new RuntimeException("get admin account failed!");
        }
    }
}
