package com.codevibe.web.dripworld.handler.types;

public class ValidationFailedException extends RuntimeException {
    public ValidationFailedException() {
    }

    public ValidationFailedException(String message) {
        super(message);
    }

    public ValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationFailedException(Throwable cause) {
        super(cause);
    }
}
