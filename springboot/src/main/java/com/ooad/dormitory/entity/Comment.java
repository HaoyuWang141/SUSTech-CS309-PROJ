package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String publisherId;
    private Integer dormitoryId;
    private String content;
    private Timestamp publishTime;
    @TableField(exist = false)
    private StudentAccount publisher;
    @TableField(exist = false)
    private Dormitory dormitory;
    @TableField(exist = false)
    private List<Reply> replyList;
}
