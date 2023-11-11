package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeamFavoriteDorm {
    @TableId(type = IdType.INPUT)
    private Integer teamId;
    @TableId(type = IdType.INPUT)
    private Integer dormitoryId;
    @TableField(exist = false)
    private Dormitory dormitory;
}
