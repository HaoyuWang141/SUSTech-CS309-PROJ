package com.ooad.dormitory.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.corundumstudio.socketio.*;
import com.ooad.dormitory.entity.ChatMessage;
import com.ooad.dormitory.mapper.ChatMessageMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
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

        server.addEventListener("userList", String.class, (client, data, ackSender) -> {
            QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();
            wrapper.isNotNull("receiver_id").and(i -> i.like("receiver_id", data).or().like("sender_id", data));
            List<ChatMessage> chatMessages = chatMessageMapper.selectList(wrapper);
            Set<String> userSet = new HashSet<>();
            for (ChatMessage chatMessage : chatMessages) {
                userSet.add(chatMessage.getSenderId());
                userSet.add(chatMessage.getReceiverId());
            }
            userSet.remove(data);
            client.sendEvent("userList", userSet.stream().sorted().toList());
        });

        server.addEventListener("privateHistory", QueryHistory.class, (client, data, ackSender) -> {
            System.out.println(data);

            QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();
            wrapper.like("sender_id", data.myId);
            wrapper.like("receiver_id", data.queryId);
            List<ChatMessage> chatMessages = chatMessageMapper.selectList(wrapper);
            wrapper = new QueryWrapper<>();
            wrapper.like("sender_id", data.queryId);
            wrapper.like("receiver_id", data.myId);
            chatMessages.addAll(chatMessageMapper.selectList(wrapper));
            client.sendEvent("history", chatMessages.stream()
                    .sorted(Comparator.comparing(ChatMessage::getTimestamp))
                    .toList());
        });

        server.addEventListener("teamHistory", QueryHistory.class, (client, data, ackSender) -> {
            if (data.queryId == null) {
                return;
            }
            int teamId = Integer.parseInt(data.queryId);
            QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();
            wrapper.eq("team_id", teamId);
            List<ChatMessage> chatMessages = chatMessageMapper.selectList(wrapper);
            client.sendEvent("history", chatMessages.stream()
                    .sorted(Comparator.comparing(ChatMessage::getTimestamp))
                    .toList());
        });

        server.addEventListener("privateMessage", ChatObject.class, (client, data, ackSender) -> {
            System.out.println(data);
            ChatMessage chatMessage = new ChatMessage(null, data.senderUserId, data.targetId, null, data.getMessage(), null, null);
            chatMessageMapper.insert(chatMessage);
            chatMessage = chatMessageMapper.selectById(chatMessage.getId());

            UUID mySessionId = client.getSessionId();
            server.getClient(mySessionId).sendEvent("message", chatMessage);
            UUID targetSessionId = userSessionMap.get(data.targetId);
            if (targetSessionId != null) {
                server.getClient(targetSessionId).sendEvent("message", chatMessage);
            }
        });

        server.addEventListener("joinTeam", String.class, (client, room, ackSender) -> {
            if (room == null) {
                return;
            }
            client.joinRoom(room);
        });

        server.addEventListener("teamMessage", ChatObject.class, (client, data, ackSender) -> {
            ChatMessage chatMessage = new ChatMessage(null, data.senderUserId, null, Integer.parseInt(data.targetId), data.getMessage(), null, null);
            chatMessageMapper.insert(chatMessage);
            chatMessage = chatMessageMapper.selectById(chatMessage.getId());
            server.getRoomOperations(data.targetId).sendEvent("message", chatMessage);
        });

        server.start();
    }

    @PreDestroy
    private void stopServer() {
        server.stop();
    }

    // ChatObject类，用于封装聊天消息
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class ChatObject {
        private String senderUserId;
        private String targetId; // 用于一对一消息
        private String message;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class QueryHistory {
        private String myId;
        private String queryId; // 另一用户或群聊id
    }
}

