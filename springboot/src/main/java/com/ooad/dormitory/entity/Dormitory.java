package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class Dormitory {
    @TableId(type = IdType.AUTO)
    private Integer dormitoryId;
    private Integer floor;
    private String roomNumber;
    private Integer bedCount;
    private String description;
    private Integer layoutId;
    @TableField(exist = false)
    private Layout layout;
    private Boolean isEmpty;
    private String gender;
    private String degree;
    private Integer buildingId;
    @TableField(exist = false)
    private Building building;
}

