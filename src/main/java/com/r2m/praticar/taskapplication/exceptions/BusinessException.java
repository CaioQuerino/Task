package com.r2m.praticar.taskapplication.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    protected HttpStatus status = null;
    
    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    
    public BusinessException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
    
    public HttpStatus getStatus() {
        return status;
    }
}