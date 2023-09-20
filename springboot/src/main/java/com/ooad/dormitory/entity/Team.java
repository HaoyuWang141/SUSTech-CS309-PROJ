package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class Team {
    @TableId(type = IdType.AUTO)
    private Integer teamId;
    @TableField(exist = false)
    private Dormitory dormitory;

    public Team(Integer teamId, Dormitory dormitory) {
        this.teamId = teamId;
        this.dormitory = dormitory;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Dormitory getDormitoryId() {
        return dormitory;
    }

    public void setDormitoryId(Dormitory dormitoryId) {
        this.dormitory = dormitoryId;
    }
}