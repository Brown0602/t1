package com.tuaev.task.service;

import com.tuaev.task.TaskStatus;
import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.entity.Task;
import com.tuaev.task.entity.User;
import com.tuaev.task.exception.NotFoundTaskException;
import com.tuaev.task.repository.TaskRepository;
import com.tuaev.task.util.mapper.TaskDTOMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DefaultTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private KafkaProducerService kafkaProducerService;
    @Mock
    private UserService userService;
    @InjectMocks
    private DefaultTaskService defaultTaskService;
    private Task task;
    private User user;
    private final List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUser() {
        user = new User("Владислав", "Туаев", 24);
        task = new Task("Домашнее задание", "Написать тесты", TaskStatus.CREATED.getValue(), user);
        tasks.add(task);
    }

    @Test
    void saveTest() {
        Mockito.when(userService.findById(1L)).thenReturn(user);
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);
        TaskDTO taskDTO = defaultTaskService.save(TaskDTOMapper.toTaskDTO(task));
        Assertions.assertThat(taskDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(TaskDTOMapper.toTaskDTO(task));
        Mockito.verify(kafkaProducerService)
                .sendMessage(Mockito.argThat(producerRecord ->
                producerRecord.key()
                        .equals(String.valueOf(task.getId())) && producerRecord.value().equals(TaskStatus.CREATED.getValue())));
        Mockito.verify(taskRepository).save(Mockito.any(Task.class));
    }

    @Test
    void deleteByIdTest() {
        Mockito.doNothing().when(taskRepository).deleteById(1L);
        defaultTaskService.deleteById(1L);
        Mockito.verify(taskRepository).deleteById(1L);
    }


    @Test
    void updateByIdTest() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        task.setStatus(TaskStatus.AT_WORK.getValue());
        TaskDTO taskDTO = defaultTaskService.updateById(1L, TaskDTOMapper.toTaskDTO(task));
        Assertions.assertThat(taskDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(TaskDTOMapper.toTaskDTO(task));
        Mockito.verify(kafkaProducerService)
                .sendMessage(Mockito.argThat(producerRecord ->
                        producerRecord.key()
                                .equals(String.valueOf(task.getId())) && producerRecord.value().equals(TaskStatus.AT_WORK.getValue())));
    }

    @Test
    void updateByIdNotFoundTaskExceptionOrFindByIdNotFountTaskExceptionTest() {
        Mockito.when(taskRepository.findById(2L)).thenThrow(new NotFoundTaskException());
        Assertions.assertThatThrownBy(() -> defaultTaskService.findById(2L))
                .isInstanceOf(NotFoundTaskException.class);
    }

    @Test
    void findByIdTest() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        TaskDTO taskDTO = defaultTaskService.findById(1L);
        Assertions.assertThat(taskDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(TaskDTOMapper.toTaskDTO(task));
    }

    @Test
    void findAllTest() {
        Mockito.when(taskRepository.findAll()).thenReturn(tasks);
        List<TaskDTO> resultTest = defaultTaskService.findAll();
        Assertions.assertThat(resultTest).hasSize(tasks.size());
        for (int i = 0; i < resultTest.size(); i++) {
            Assertions.assertThat(resultTest.get(i))
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(TaskDTOMapper.toTaskDTO(tasks.get(i)));
        }
    }
}
