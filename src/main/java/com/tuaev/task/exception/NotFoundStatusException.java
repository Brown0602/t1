package com.tuaev.task.exception;

public class NotFoundStatusException extends RuntimeException {

    public NotFoundStatusException() {
    }

    public NotFoundStatusException(String message) {
        super(message);
    }
}
