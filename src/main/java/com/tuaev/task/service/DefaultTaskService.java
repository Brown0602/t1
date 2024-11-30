package com.tuaev.task.service;

import com.tuaev.task.annotation.LogBefore;
import com.tuaev.task.annotation.LogException;
import com.tuaev.task.annotation.LogMethod;
import com.tuaev.task.annotation.ResultHandler;
import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.entity.Task;
import com.tuaev.task.entity.User;
import com.tuaev.task.exception.NotFoundTaskException;
import com.tuaev.task.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultTaskService implements TaskService{

    private final TaskRepository taskRepository;
    private final UserService userService;

    public DefaultTaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @ResultHandler
    @LogMethod
    @Transactional
    @Override
    public Task save(TaskDTO taskDTO) {
        Optional<User> user = userService.findById(1L);
        return  taskRepository.save(new Task(taskDTO.getTitle(), taskDTO.getDescription(), user.get()));
    }

    @LogBefore
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @LogMethod
    @LogException
    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(()-> new NotFoundTaskException("Задача не найдена"));
    }

    @LogMethod
    @LogException
    @Override
    public Task updateById(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(()-> new NotFoundTaskException("Задача не найдена"));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        return taskRepository.save(task);
    }

    @LogBefore
    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public String toString() {
        return "DefaultTaskService";
    }
}
