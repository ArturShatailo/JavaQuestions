package com.study.javaquestions.util.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<MyGlobalExceptionHandler> handleDeleteException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user was deleted"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex) {
        return new ResponseEntity<>(new MyGlobalExceptionHandler(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @AllArgsConstructor
    private static class MyGlobalExceptionHandler {
        private String message;
        private final Date date = new Date();
    }
}
