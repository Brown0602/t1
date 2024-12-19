package com.tuaev.task.controller.handler;

import com.tuaev.task.dto.ResponseDTO;
import com.tuaev.task.exception.NotFoundStatusException;
import com.tuaev.task.exception.NotFoundTaskException;
import com.tuaev.task.exception.NotFoundUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@ControllerAdvice
public class TaskExceptionsHandler {

    @ExceptionHandler(NotFoundTaskException.class)
    @ResponseBody
    public ResponseDTO notFoundTaskException(NotFoundTaskException exception) {
        return new ResponseDTO(LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundUserException.class)
    @ResponseBody
    public ResponseDTO notFoundUserException(NotFoundUserException exception) {
        return new ResponseDTO(LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundStatusException.class)
    @ResponseBody
    public ResponseDTO notFoundStatusException(NotFoundStatusException exception) {
        return new ResponseDTO(LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}