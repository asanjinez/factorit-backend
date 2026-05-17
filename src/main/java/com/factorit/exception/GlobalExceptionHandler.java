package com.factorit.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FactoritException.class)
    public ResponseEntity<ErrorResponse> handleFactoritException(
            FactoritException exception,
            HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(
                exception.getCode(),
                exception.getMessage(),
                exception.getStatus().value(),
                request.getRequestURI(),
                LocalDateTime.now());
        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
