package com.blog.authservice.model.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public static BusinessExceptionBuilder builder() {
        return new BusinessExceptionBuilder();
    }

    public static class BusinessExceptionBuilder {
        private String message;

        public BusinessExceptionBuilder message(String message) {
            this.message = message;
            return this;
        }

        public BusinessException build() {
            return new BusinessException(message);
        }
    }
}
