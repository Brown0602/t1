package com.tuaev.task.controller.handler;

import com.tuaev.task.exception.NotFoundTaskException;
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
    public String notFoundCoffeeMachineException(NotFoundTaskException exception) {
        return exception.getMessage();
    }
}