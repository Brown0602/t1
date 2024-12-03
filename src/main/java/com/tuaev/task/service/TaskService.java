package com.tuaev.task.service;

import com.tuaev.task.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO save(TaskDTO taskDTO);

    List<TaskDTO> findAll();

    TaskDTO findById(Long id);

    TaskDTO updateById(Long id, TaskDTO taskDTO);

    void deleteById(Long id);
}
