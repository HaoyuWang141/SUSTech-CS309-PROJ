package com.ooad.dormitory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Integer id;
    private String senderId;
    private String receiverId;
    private String teamId;
    private String content;
    private Timestamp timestamp;
    private String status;
}
