package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AllocationStage {
    @TableId(type = IdType.INPUT)
    private Integer entryYear;
    @TableId(type = IdType.INPUT)
    private Integer degree;
    private Integer gender;
    private Integer stage;
}
