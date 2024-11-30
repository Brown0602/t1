package com.tuaev.task.service;

import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.entity.Task;
import java.util.List;

public interface TaskService {

    Task save(TaskDTO taskDTO);

    List<Task> findAll();

    Task findById(Long id);

    Task updateById(Long id, TaskDTO taskDTO);

    void deleteById(Long id);
}
