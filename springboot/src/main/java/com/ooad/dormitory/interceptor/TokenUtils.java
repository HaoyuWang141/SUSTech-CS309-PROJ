package com.ooad.dormitory.interceptor;

import java.sql.Timestamp;
import java.util.UUID;

public class TokenUtils {
    public static final int DUE_TIME = 30 * 60 * 1000; // 30分钟

    public static String generateToken(String userId) {
        // 生成随机的UUID作为Token
        String token = userId + "."
                + new Timestamp(System.currentTimeMillis() + DUE_TIME) + "."
                + UUID.randomUUID();
        return token;
    }

    @Deprecated
    public static boolean validateToken(Token token) {
        // 例如检查Token的有效期等
        return token.getDueTime().compareTo(new Timestamp(System.currentTimeMillis())) > 0;
    }

    public static boolean validateToken(String token) {
        String[] tokenParts = token.split(".");
        return new Timestamp(System.currentTimeMillis()).compareTo(Timestamp.valueOf(tokenParts[1])) <= 0;
    }
}
