//package com.ooad.dormitory.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//
//    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            InputStream inputStream = request.getInputStream();
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, String> loginData = objectMapper.readValue(inputStream, Map.class);
//
//            String studentId = loginData.get("studentId");
//            String password = loginData.get("password");
//
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(studentId, password);
//
//            return authenticationManager.authenticate(authenticationToken);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
//        // Implement logic for successful authentication (e.g., generate JWT token)
//        // This method will be called when authentication is successful
//    }
//}
