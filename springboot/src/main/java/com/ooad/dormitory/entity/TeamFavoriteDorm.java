package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamFavoriteDorm {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer teamId;
    private Integer dormitoryId;
    @TableField(exist = false)
    private Dormitory dormitory;
}
