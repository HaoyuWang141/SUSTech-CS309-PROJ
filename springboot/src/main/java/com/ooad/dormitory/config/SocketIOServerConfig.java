package com.ooad.dormitory.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.corundumstudio.socketio.*;
import com.ooad.dormitory.entity.ChatMessage;
import com.ooad.dormitory.mapper.ChatMessageMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class SocketIOServerConfig {

    private final SocketIOServer server;
    private final Map<String, UUID> userSessionMap = new ConcurrentHashMap<>();

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    public SocketIOServerConfig() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8083); // 选择适合的端口

        this.server = new SocketIOServer(config);
    }

    @PostConstruct
    private void startServer() {
        server.addConnectListener(client -> {
            String studentId = client.getHandshakeData().getSingleUrlParam("studentId");
            userSessionMap.put(studentId, client.getSessionId());
            System.out.println("Client connected: " + studentId + " with session " + client.getSessionId());
        });

        server.addDisconnectListener(client -> {
            userSessionMap.values().remove(client.getSessionId());
            System.out.println("Client disconnected: " + client.getSessionId());
        });

        server.addEventListener("privateHistory", ChatObject.class, (client, data, ackSender) -> {
            QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();
            wrapper.like("sender_id", data.senderUserId);
            wrapper.like("receiver_id", data.targetUserId);
            List<ChatMessage> chatMessages = chatMessageMapper.selectList(wrapper);
            wrapper = new QueryWrapper<>();
            wrapper.like("sender_id", data.targetUserId);
            wrapper.like("receiver_id", data.senderUserId);
            chatMessages.addAll(chatMessageMapper.selectList(wrapper));
            client.sendEvent("history", chatMessages.stream()
                    .sorted(Comparator.comparing(ChatMessage::getTimestamp))
                    .toList());
        });

        server.addEventListener("teamHistory", ChatObject.class, (client, data, ackSender) -> {
            QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();
            wrapper.eq("team_id", data.teamId);
            List<ChatMessage> chatMessages = chatMessageMapper.selectList(wrapper);
            client.sendEvent("history", chatMessages.stream()
                    .sorted(Comparator.comparing(ChatMessage::getTimestamp))
                    .toList());
        });

        server.addEventListener("privateMessage", ChatObject.class, (client, data, ackSender) -> {
            UUID targetSessionId = userSessionMap.get(data.getTargetUserId());
            if (targetSessionId != null) {
                server.getClient(targetSessionId).sendEvent("message", data.getMessage());
            }
        });


        server.addEventListener("joinTeam", String.class, (client, room, ackSender) -> {
            client.joinRoom(room);
        });

        server.addEventListener("teamMessage", ChatObject.class, (client, data, ackSender) -> {
            server.getRoomOperations(data.getTeamId()).sendEvent("message", data.getMessage());
        });

        server.start();
    }

    @PreDestroy
    private void stopServer() {
        server.stop();
    }

    // ChatObject类，用于封装聊天消息
    @AllArgsConstructor
    @Data
    private static class ChatObject {
        private String senderUserId;
        private String targetUserId; // 用于一对一消息
        private String teamId; // 用于群聊
        private String message;
    }
}

