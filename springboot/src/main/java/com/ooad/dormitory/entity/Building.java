package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class Building {
    @TableId(type = IdType.AUTO)
    private Integer buildingId;
    private String buildingName;
    private String description;
    @TableField(exist = false)
    private Region region;

    public Building() {
    }

    public Building(Integer buildingId, String buildingName, String description) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.description = description;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}

