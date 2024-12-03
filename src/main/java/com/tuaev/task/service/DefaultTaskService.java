package com.tuaev.task.service;

import com.tuaev.task.annotation.LogBefore;
import com.tuaev.task.annotation.LogException;
import com.tuaev.task.annotation.LogMethod;
import com.tuaev.task.annotation.ResultHandler;
import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.entity.Task;
import com.tuaev.task.entity.User;
import com.tuaev.task.exception.NotFoundTaskException;
import com.tuaev.task.exception.NotFoundUserException;
import com.tuaev.task.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public DefaultTaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @ResultHandler
    @LogMethod
    @LogException
    @Transactional
    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        User user = userService.findById(1L).orElseThrow(()-> new NotFoundUserException("Пользователь не найден"));
        Task task = new Task(taskDTO.getTitle(), taskDTO.getDescription(), user);
        taskRepository.save(task);
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setUser(task.getUser());
        return taskDTO;
    }

    @LogBefore
    @Override
    public List<TaskDTO> findAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(task ->
                new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getUser())).toList();
    }

    @LogMethod
    @LogException
    @Override
    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundTaskException("Задача не найдена"));
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getUser());
    }

    @LogMethod
    @LogException
    @Override
    public TaskDTO updateById(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundTaskException("Задача не найдена"));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        taskRepository.save(task);
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getUser());
    }

    @LogBefore
    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
