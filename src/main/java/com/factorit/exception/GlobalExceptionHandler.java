package com.factorit.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FactoritException.class)
    public ResponseEntity<ErrorResponse> handleFactoritException(
            FactoritException exception,
            HttpServletRequest request) {
        return buildResponse(exception.getCode(), exception.getMessage(), exception.getStatus(), request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpServletRequest request) {
        return buildResponse("INVALID_REQUEST_BODY", "Invalid request body", HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(HttpServletRequest request) {
        return buildResponse("INVALID_PARAMETER", "Invalid parameter", HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(HttpServletRequest request) {
        return buildResponse("MISSING_PARAMETER", "Required parameter is missing", HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpServletRequest request) {
        return buildResponse("METHOD_NOT_ALLOWED", "Method not allowed", HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request) {
        return buildResponse("INTERNAL_ERROR", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            String code,
            String message,
            HttpStatus status,
            HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(code, message, status.value(),
                request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.status(status).body(response);
    }
}
