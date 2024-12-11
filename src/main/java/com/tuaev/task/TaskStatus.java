package com.tuaev.task;

import java.util.List;

public enum TaskStatus {

    CREATED("Создана"), AT_WORK("В работе"), FINISHED("Закончена");

    TaskStatus(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    public static List<TaskStatus> getAllStatus(){
        return List.of(TaskStatus.CREATED, TaskStatus.AT_WORK, TaskStatus.FINISHED);
    }
}
