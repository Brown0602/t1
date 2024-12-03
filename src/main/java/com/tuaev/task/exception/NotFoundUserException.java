package com.tuaev.task.exception;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException() {
        super();
    }

    public NotFoundUserException(String message) {
        super(message);
    }
}
