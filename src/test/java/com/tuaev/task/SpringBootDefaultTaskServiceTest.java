package com.tuaev.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuaev.task.dto.ResponseDTO;
import com.tuaev.task.dto.TaskDTO;
import com.tuaev.task.service.KafkaProducerService;
import com.tuaev.task.service.TaskService;
import com.tuaev.task.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SpringBootDefaultTaskServiceTest extends Containers {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private KafkaProducerService kafkaProducerService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(kafkaProducerService)
                .sendMessage(Mockito.any());
    }

    @Test
    void findByIdTest() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Домашнее задание");
        taskDTO.setDescription("Написать модульные и интеграционные тесты");
        taskDTO.setStatus(TaskStatus.CREATED.getValue());
        taskService.save(taskDTO);
        taskDTO = taskService.findById(1L);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(taskDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(taskDTO.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(taskDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(taskDTO.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(taskDTO.getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.userName").value(taskDTO.getUser().getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.lastName").value(taskDTO.getUser().getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.age").value(taskDTO.getUser().getAge()));
    }

    @Test
    void deleteById() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Домашнее задание");
        taskDTO.setDescription("Написать модульные и интеграционные тесты");
        taskDTO.setStatus(TaskStatus.CREATED.getValue());
        taskService.save(taskDTO);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/tasks/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveTest() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Домашнее задание");
        taskDTO.setDescription("Написать модульные и интеграционные тесты");
        taskDTO.setStatus(TaskStatus.CREATED.getValue());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(kafkaProducerService)
                .sendMessage(Mockito.any());
        taskDTO = taskService.findById(1L);
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(taskDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(taskDTO.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(taskDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(taskDTO.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(taskDTO.getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.userName").value(taskDTO.getUser().getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.lastName").value(taskDTO.getUser().getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.age").value(taskDTO.getUser().getAge()));
    }

    @Test
    void notFoundTaskException() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO(LocalDate.now(), "Задача не найдена", HttpStatus.NOT_FOUND.value());
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Домашнее задание");
        taskDTO.setDescription("Написать модульные и интеграционные тесты");
        taskDTO.setStatus(TaskStatus.CREATED.getValue());
        taskService.save(taskDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/tasks/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localDate").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(responseDTO.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value(responseDTO.getHttpStatus()));
    }

    @Test
    void updateByIdTest() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Домашнее задание");
        taskDTO.setDescription("Написать модульные и интеграционные тесты");
        taskDTO.setStatus(TaskStatus.CREATED.getValue());
        taskService.save(taskDTO);
        taskDTO.setStatus(TaskStatus.AT_WORK.getValue());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/tasks/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        taskDTO = taskService.findById(1L);
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(taskDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(taskDTO.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(taskDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(taskDTO.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(taskDTO.getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.userName").value(taskDTO.getUser().getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.lastName").value(taskDTO.getUser().getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.age").value(taskDTO.getUser().getAge()));
    }

    @Test
    void findAllTest() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Домашнее задание");
        taskDTO.setDescription("Написать модульные и интеграционные тесты");
        taskDTO.setStatus(TaskStatus.CREATED.getValue());
        taskService.save(taskDTO);
        taskDTO = taskService.findById(1L);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(taskDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(taskDTO.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(taskDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.id").value(taskDTO.getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.userName").value(taskDTO.getUser().getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.lastName").value(taskDTO.getUser().getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.age").value(taskDTO.getUser().getAge()));
    }
}
