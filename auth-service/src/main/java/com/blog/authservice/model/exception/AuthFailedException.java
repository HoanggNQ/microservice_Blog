package com.blog.authservice.model.exception;

import com.blog.authservice.model.dto.ResponseObject;
import org.springframework.http.HttpStatus;

public class AuthFailedException extends AuthServicesException {
    public AuthFailedException(final String message) {
    super(message);
        this.errorResponse = ResponseObject.builder()
            .code("AUTH_FAILED")
                .message(message)
                .data(null)
                .isSuccess(false)
                .status(HttpStatus.UNAUTHORIZED)
                .build();
}
}