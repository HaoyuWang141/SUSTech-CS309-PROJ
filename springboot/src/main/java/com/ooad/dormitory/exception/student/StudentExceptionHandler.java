package com.ooad.dormitory.exception.student;

import com.ooad.dormitory.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.ooad.dormitory.controller.student"})
public class StudentExceptionHandler {

    @ExceptionHandler(DatabaseException.class) // 后端错误：数据库错误
    public ResponseEntity<String> handleRuntimeException(DatabaseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error in database occurred: " + ex.getMessage());
    }

    @ExceptionHandler(InternalServerException.class) // 后端错误：程序内部错误
    public ResponseEntity<String> handleRuntimeException(InternalServerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error in program occurred: " + ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class) // 前端错误：请求不合法
    public ResponseEntity<String> handleRuntimeException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error in request occurred: " + ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class) // 前端错误：查询值为空
    public ResponseEntity<String> handleRuntimeException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("An error in database occurred: " + ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class) // 前端错误：未登录
    public ResponseEntity<String> handleRuntimeException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + ex.getMessage());
    }

    @ExceptionHandler(FrontEndException.class)
    public ResponseEntity<String> handleRuntimeException(FrontEndException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("An error in front end occurred: " + ex.getMessage());
    }

    @ExceptionHandler(BackEndException.class)
    public ResponseEntity<String> handleRuntimeException(BackEndException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error in back end occurred: " + ex.getMessage());
    }
}
