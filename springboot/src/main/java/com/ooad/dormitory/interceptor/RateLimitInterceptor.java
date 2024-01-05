package com.ooad.dormitory.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final Map<String, Integer> requestCount = new ConcurrentHashMap<>();

    private static final Map<String, AccessInfo> requestInfo = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("RateLimitInterceptor start pre-handling");
//        String ipAddress = request.getRemoteAddr();
//        if (requestCount.containsKey(ipAddress)) {
//            int count = requestCount.get(ipAddress);
//            if (count > 10) { // 设置一个适当的阈值
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                return false;
//            } else {
//                requestCount.put(ipAddress, count + 1);
//            }
//        } else {
//            requestCount.put(ipAddress, 1);
//        }
//        return true;

        String ipAddress = request.getRemoteAddr();

        // 获取或创建访问信息
        AccessInfo accessInfo = requestInfo.computeIfAbsent(ipAddress, k -> new AccessInfo());

        // 检查访问频率
        if (isRateLimited(accessInfo)) {
//            response.setStatus(HttpServletResponse.SC_TOO_MANY_REQUESTS);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            System.out.println("RateLimitInterceptor denied");
            return false;
        }

        // 更新访问信息
        accessInfo.incrementCount();
        accessInfo.setLastAccessTime(System.currentTimeMillis());

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


    private boolean isRateLimited(AccessInfo accessInfo) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - accessInfo.getLastAccessTime();
        System.out.println(currentTime);
        System.out.println(elapsedTime);

        // 设置访问间隔和访问次数的阈值
        return elapsedTime < 1000 && accessInfo.getCount() > 10; // 1秒内超过10次请求视为频繁访问
    }

    private static class AccessInfo {
        private int count = 0;
        private long lastAccessTime = 0;

        public int getCount() {
            return count;
        }

        public void incrementCount() {
            count++;
        }

        public long getLastAccessTime() {
            return lastAccessTime;
        }

        public void setLastAccessTime(long lastAccessTime) {
            this.lastAccessTime = lastAccessTime;
        }
    }

}