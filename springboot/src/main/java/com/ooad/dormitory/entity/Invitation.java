package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invitation {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String inviterId;
    private String inviteeId;
    private Timestamp time;
    @TableField(exist = false)
    private StudentAccount inviter;
    @TableField(exist = false)
    private StudentAccount invitee;
}
