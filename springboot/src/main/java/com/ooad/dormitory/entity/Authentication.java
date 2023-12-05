package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@NoArgsConstructor
@Data
public class Authentication {
    @TableId(type = IdType.INPUT)
    private String studentId;
    private String studentPassword;
    private String token;
    private Time tokenFailureTime;
    private Integer onlineAmount;
}
