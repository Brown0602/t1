package com.tuaev.task.util.mapper;

import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.entity.Task;

public class TaskDTOMapper {

    private TaskDTOMapper() {
    }

    public static TaskDTO toTaskDTO(Task task) {
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), UserDTOMapper.toUserDTO(task.getUser()));
    }
}
