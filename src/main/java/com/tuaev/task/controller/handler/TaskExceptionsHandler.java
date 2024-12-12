package com.tuaev.task.controller.handler;

import com.tuaev.task.exception.NotFoundStatusException;
import com.tuaev.task.exception.NotFoundTaskException;
import com.tuaev.task.exception.NotFoundUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TaskExceptionsHandler {

    @ExceptionHandler(NotFoundTaskException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFoundTaskException(NotFoundTaskException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotFoundUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFoundUserException(NotFoundUserException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotFoundStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFoundStatusException(NotFoundStatusException exception) {
        return exception.getMessage();
    }
}