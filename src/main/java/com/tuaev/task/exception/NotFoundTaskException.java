package com.tuaev.task.exception;

public class NotFoundTaskException extends RuntimeException {

    public NotFoundTaskException() {
        super();
    }

    public NotFoundTaskException(String message) {
        super(message);
    }
}
