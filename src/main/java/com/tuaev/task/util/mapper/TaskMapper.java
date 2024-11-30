package com.tuaev.task.util.mapper;

import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.entity.Task;

public class TaskMapper {

    public static Task toTask(TaskDTO taskDTO){
        return new Task(taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getStatus(), taskDTO.getUser());
    }
}
