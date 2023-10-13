package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.beans.Transient;

public class Dormitory {
    @TableId(type = IdType.AUTO)
    private Integer dormitoryId;
    private Integer floor;
    private String roomNumber;
    private Integer bedCount;
    private String description;
    @TableField(exist = false)
    private Layout layout;
    private Boolean isEmpty;
    private String gender;
    private String degree;
    @TableField(exist = false)
    private Building building;

    public Dormitory() {
    }

    public Dormitory(Integer dormitoryId, Integer floor, String roomNumber, Integer bedCount, String description, Layout layout, Boolean isEmpty, String gender, String degree) {
        this.dormitoryId = dormitoryId;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.bedCount = bedCount;
        this.description = description;
        this.layout = layout;
        this.isEmpty = isEmpty;
        this.gender = gender;
        this.degree = degree;
    }

    public Integer getDormitoryId() {
        return dormitoryId;
    }

    public void setDormitoryId(Integer dormitoryId) {
        this.dormitoryId = dormitoryId;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}

