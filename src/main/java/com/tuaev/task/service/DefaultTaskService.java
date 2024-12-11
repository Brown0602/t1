package com.tuaev.task.service;

import com.tuaev.task.TaskStatus;
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
import com.tuaev.task.util.mapper.TaskDTOMapper;
import com.tuaev.task.util.mapper.TaskMapper;
import jakarta.transaction.Transactional;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public DefaultTaskService(TaskRepository taskRepository, UserService userService, KafkaTemplate<String, String> kafkaTemplate) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @ResultHandler
    @LogMethod
    @LogException
    @Transactional
    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        User user = userService.findById(1L).orElseThrow(() -> new NotFoundUserException("Пользователь не найден"));
        taskDTO.setStatus(TaskStatus.CREATED.getValue());
        return TaskDTOMapper.toTaskDTO(taskRepository.save(TaskMapper.toTask(taskDTO, user)));
    }

    @LogBefore
    @Override
    public List<TaskDTO> findAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(TaskDTOMapper::toTaskDTO).toList();
    }

    @LogMethod
    @LogException
    @Override
    public TaskDTO findById(Long id) {
        return TaskDTOMapper.toTaskDTO(taskRepository.findById(id).orElseThrow(() ->
                new NotFoundTaskException("Задача не найдена")));
    }

    @LogMethod
    @LogException
    @Transactional
    @Override
    public TaskDTO updateById(Long id, TaskDTO taskDTO) {
        isStatus(taskDTO);
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundTaskException("Задача не найдена"));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        taskRepository.save(task);
        kafkaTemplate.send(new ProducerRecord<>("task_status", String.valueOf(task.getId()), taskDTO.getStatus()));
        return TaskDTOMapper.toTaskDTO(task);
    }

    private void isStatus(TaskDTO taskDTO) {
        if (TaskStatus.getAllStatus().stream().noneMatch(taskStatus ->
                taskStatus.getValue().equals(taskDTO.getStatus()))) {
            throw new NotFoundTaskException("Данного статуса задачи не существует");
        }
    }

    @LogBefore
    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
