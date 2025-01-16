package com.codevibe.web.dripworld.handler.types;

public class DBFailureException extends RuntimeException{
    public DBFailureException() {

    }

    public DBFailureException(String message) {
        super(message);
    }

    public DBFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBFailureException(Throwable cause) {
        super(cause);
    }
}
