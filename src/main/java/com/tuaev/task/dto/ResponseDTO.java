package com.tuaev.task.dto;

import java.time.LocalDate;

public class ResponseDTO {

    private LocalDate localDate;
    private String message;
    private int httpStatus;

    public ResponseDTO() {
    }

    public ResponseDTO(LocalDate localDate, String message, int httpStatus) {
        this.localDate = localDate;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
