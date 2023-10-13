package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Time;

public class StudentAccount {
    @TableId(type = IdType.INPUT)
    private String studentId;
    private String name;
    private String gender;
    private String photoUrl;
    private String description;
    private Time sleepTime;
    private Time wakeUpTime;
    private Integer airConditionerTemperature;
    private Boolean isSnoring;
    private String qq;
    private String email;
    private String wechat;
    @TableField(exist = false)
    private Team team;


    public StudentAccount() {

    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Time sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Time getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(Time wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public Integer getAirConditionerTemperature() {
        return airConditionerTemperature;
    }

    public void setAirConditionerTemperature(Integer airConditionerTemperature) {
        this.airConditionerTemperature = airConditionerTemperature;
    }

    public Boolean getSnoring() {
        return isSnoring;
    }

    public void setSnoring(Boolean snoring) {
        isSnoring = snoring;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

