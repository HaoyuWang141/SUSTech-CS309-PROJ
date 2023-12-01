package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer publisherId;
    private Integer entryYear;
    private Integer degree;
    private Integer gender;
    private String title;
    private String content;
    private Time publishTime;
}
