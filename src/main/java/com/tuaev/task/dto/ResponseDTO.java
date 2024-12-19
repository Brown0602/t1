package com.tuaev.task.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseDTO {

    private LocalDateTime localDateTime;
    private String message;
    private HttpStatus httpStatus;

    public ResponseDTO(LocalDateTime localDateTime, String message, HttpStatus httpStatus) {
        this.localDateTime = localDateTime;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
