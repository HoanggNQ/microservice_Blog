package com.blog.authservice.model.exception;

import com.blog.authservice.model.dto.ResponseObject;
import org.springframework.http.HttpStatus;

public class ActionFailedException extends AuthServicesException {
    public ActionFailedException(String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code("ACTION_FAILED")
                .message(message)
                .data(null)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }

    public ActionFailedException(String code,String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
                .code(code)
                .data(null)
                .message(message)
                .isSuccess(false)
                .status(HttpStatus.OK)
                .build();
    }
}
