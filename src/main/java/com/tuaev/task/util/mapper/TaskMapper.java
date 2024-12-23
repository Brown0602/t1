package com.tuaev.task.util.mapper;

import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.dto.UserDTO;
import com.tuaev.task.entity.Task;
import com.tuaev.task.entity.User;

public class TaskMapper {

    private TaskMapper() {
    }

    public static Task toTask(TaskDTO taskDTO, User user) {
        return new Task(taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getStatus(), user);
    }
}
