package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String publisherId;
    private Integer replyingCommentId;
    private Integer dormitoryId;
    private String content;
    private Time publishTime;
    @TableField(exist = false)
    private StudentAccount publisher;
    @TableField(exist = false)
    private Comment replyingComment;
    @TableField(exist = false)
    private Dormitory dormitory;
}
