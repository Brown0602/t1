package com.tuaev.task;

public enum TaskStatus {

    CREATED("Создана"), AT_WORK("В работе"), FINISHED("Закончена");

    TaskStatus(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
