package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String senderId;
    private String receiverId;
    private Integer teamId;
    private String content;
    private Timestamp timestamp;
    private String status;
}
