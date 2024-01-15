package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Team {
    @TableId(type = IdType.AUTO)
    private Integer teamId;
    private Integer dormitoryId;
    private String ownerId;
    @TableField(exist = false)
    private Dormitory dormitory;
    @TableField(exist = false)
    private List<StudentAccount> teamMembers;
    @TableField(exist = false)
    private StudentAccount owner;
    @TableField(exist = false)
    private List<Dormitory> favoriteDormitories;
}