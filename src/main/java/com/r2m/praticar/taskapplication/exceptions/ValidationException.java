package com.r2m.praticar.taskapplication.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends BusinessException {
    private static HttpStatus status;

    public ValidationException(String message) {
        super(message, status);
    }
    
    public ValidationException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}