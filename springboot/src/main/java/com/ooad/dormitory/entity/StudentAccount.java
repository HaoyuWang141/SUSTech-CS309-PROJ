package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
@NoArgsConstructor
@Data
public class StudentAccount {
    @TableId(type = IdType.INPUT)
    private String studentId;
    private String name;
    private String gender;
    private String photoUrl;
    private Time sleepTime;
    private Time wakeUpTime;
    private Integer airConditionerTemperature;
    private Boolean snore;
    private String qq;
    private String email;
    private String wechat;
    private Integer teamId;
    @TableField(exist = false)
    private Team team;
}