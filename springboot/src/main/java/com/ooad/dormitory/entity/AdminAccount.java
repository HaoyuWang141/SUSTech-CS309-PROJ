package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class AdminAccount {
    @TableId(type = IdType.AUTO)
    private Integer accountId;
    private String accountName;
    private String password;

    public AdminAccount() {
    }

    public AdminAccount(Integer accountId, String accountName, String password) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.password = password;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

