package com.ooad.dormitory.interceptor;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtils {
    public static final int DUE_TIME = 30 * 60 * 1000; // 30分钟
    public static final int MAX_ONLINE_AMOUNT = 1;

    //    private Map<String, String> tokenMap = new ConcurrentHashMap<>();
    private static Map<String, String> tokenMap = new HashMap<>();
    private static Map<String, Integer> onlineAmountMap = new HashMap<>();

    public static String generateToken(String userId) {
        // 生成随机的UUID作为Token
        String token = userId + "."
                + new Timestamp(System.currentTimeMillis() + DUE_TIME) + "."
                + getOnlineAmount(userId) + "."
                + UUID.randomUUID();
        tokenMap.put(userId, token);
        onlineAmountMap.put(userId, getOnlineAmount(userId) + 1);
        return token;
    }

    public static boolean checkOnlineAmount(String userId) {
        return getOnlineAmount(userId) < MAX_ONLINE_AMOUNT;
    }

//    @Deprecated
//    public static boolean validateToken(Token token) {
//        // 例如检查Token的有效期等
//        return token.getDueTime().compareTo(new Timestamp(System.currentTimeMillis())) > 0;
//    }

    public static boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        System.out.println(token);
        String[] tokenParts = token.split("\\.");
        String userId = tokenParts[0];
        String dueTime = tokenParts[1];
        if (new Timestamp(System.currentTimeMillis()).compareTo(Timestamp.valueOf(tokenParts[1])) <= 0) {
            return true;
        }
        else {
            onlineAmountMap.put(userId, Math.max(0, getOnlineAmount(userId) - 1));
            return false;
        }
    }

    private static int getOnlineAmount(String userId) {
        return onlineAmountMap.getOrDefault(userId, 0);
    }
}
