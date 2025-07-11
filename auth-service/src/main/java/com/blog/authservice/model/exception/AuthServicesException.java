package com.blog.authservice.model.exception;


import com.blog.authservice.model.dto.ResponseObject;

public class AuthServicesException extends RuntimeException {
    protected ResponseObject errorResponse;

    protected AuthServicesException(String message) {
        super(message);
    }

    public ResponseObject getErrorResponse() {
        return errorResponse;
    }
}
