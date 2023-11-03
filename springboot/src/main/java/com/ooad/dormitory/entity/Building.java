package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Building {
    @TableId(type = IdType.AUTO)
    private Integer buildingId;
    private String buildingName;
    private String description;
    private Integer regionId;
    @TableField(exist = false)
    private Region region;
}

