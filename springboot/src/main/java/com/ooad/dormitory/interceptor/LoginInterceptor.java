package com.ooad.dormitory.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//public class LoginInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        if (request.getSession().getAttribute("user") == null) {
////            response.sendRedirect("/login");
////            return false;
////        }
//        return true;
//    }
//}


public class LoginInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 在进入Controller方法前执行，用于拦截处理逻辑
//
//        // 在这里你可以实现登录检查逻辑，如果用户未登录，可以生成并设置Token
//        if (!userIsLoggedIn()) {
////            response.sendRedirect("/student/login");
//            Token token = TokenUtils.generateToken();
//            response.setHeader("Authorization", token.getToken());
//            return false;
//        }
//        return true; // 返回true表示继续执行，返回false表示中断执行
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在进入Controller方法前执行，用于拦截处理逻辑


        // 验证token
//        String token = request.getHeader("Authorization");
//        if (!TokenUtils.validateToken(token)) {
//            response.sendRedirect("/student/login");
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 在Controller方法调用之后，渲染视图之前执行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 在整个请求完成后执行，主要用于清理资源等工作
    }
}

