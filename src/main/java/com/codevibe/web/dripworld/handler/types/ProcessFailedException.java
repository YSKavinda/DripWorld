package com.codevibe.web.dripworld.handler.types;

public class ProcessFailedException extends RuntimeException{
    public ProcessFailedException() {

    }

    public ProcessFailedException(String message) {
        super(message);
    }

    public ProcessFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessFailedException(Throwable cause){
        super(cause);
    }
}
