package com.tuaev.task.controller;

import com.tuaev.spring_boot_starter_logging_http_methods.annotations.*;
import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/")
@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @FullLoggingMethod
    @PostMapping("tasks")
    public TaskDTO create(@RequestBody TaskDTO taskDTO) {
        return taskService.save(taskDTO);
    }

    @FullLoggingMethod
    @GetMapping("tasks/{id}")
    public TaskDTO findByID(@PathVariable("id") Long id) {
        return taskService.findById(id);
    }

    @ExceptionHandlerMethod
    @ResultHandlerMethod
    @PutMapping("tasks/{id}")
    public TaskDTO update(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
        return taskService.updateById(id, taskDTO);
    }

    @FullLoggingMethod
    @DeleteMapping("tasks/{id}")
    public void delete(@PathVariable("id") Long id) {
        taskService.deleteById(id);
    }

    @FullLoggingMethod
    @GetMapping("tasks")
    public List<TaskDTO> getAllTasks() {
        return taskService.findAll();
    }
}
