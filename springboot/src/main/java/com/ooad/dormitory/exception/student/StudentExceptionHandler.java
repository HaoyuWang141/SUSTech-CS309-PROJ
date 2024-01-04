package com.ooad.dormitory.exception.student;

import com.ooad.dormitory.exception.BadRequestException;
import com.ooad.dormitory.exception.DatabaseException;
import com.ooad.dormitory.exception.InternalServerException;
import com.ooad.dormitory.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.ooad.dormitory.controller.student"})
public class StudentExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleRuntimeException(DatabaseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error in database occurred: " + ex.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<String> handleRuntimeException(InternalServerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error in program occurred: " + ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class) // 请求不合法
    public ResponseEntity<String> handleRuntimeException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class) // 查询值为空
    public ResponseEntity<String> handleRuntimeException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("An error occurred: " + ex.getMessage());
    }
}
