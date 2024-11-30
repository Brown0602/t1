package com.tuaev.task.controller;

import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.entity.Task;
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

    @PostMapping("tasks")
    public Task create(@RequestBody TaskDTO taskDTO){
        return taskService.save(taskDTO);
    }

    @GetMapping("tasks/{id}")
    public Task findByID(@PathVariable("id") Long id){
        return taskService.findById(id);
    }

    @PutMapping("tasks/{id}")
    public Task update(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO){
        return taskService.updateById(id, taskDTO);
    }

    @DeleteMapping("tasks/{id}")
    public void delete(@PathVariable("id") Long id){
        taskService.deleteById(id);
    }

    @GetMapping("tasks")
    public List<Task> getAllTasks(){
        return taskService.findAll();
    }
}
