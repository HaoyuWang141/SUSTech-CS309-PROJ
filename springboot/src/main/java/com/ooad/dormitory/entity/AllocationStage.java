package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AllocationStage {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer entryYear;
    private Integer degree;
    private Integer gender;
    private Integer stage;
    private Timestamp startTime;
    private Timestamp endTime;
}
