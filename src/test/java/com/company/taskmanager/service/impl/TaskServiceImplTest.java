package com.company.taskmanager.service.impl;

import com.company.taskmanager.dto.request.TaskRequestDTO;
import com.company.taskmanager.model.ApiResponse;
import com.company.taskmanager.model.Task;
import com.company.taskmanager.repository.TaskRepository;
import com.company.taskmanager.utility.ResponseUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskRequestDTO requestDTO;

    @BeforeEach
    void setUp(){
        requestDTO = TaskRequestDTO.builder()
                .title("Sample Task")
                .description("Sample Desc")
                .assignedTo("Dev")
                .completed(false)
                .build();
    }


//    Create Task Test
    @Test
    @DisplayName("Should we create the task when valid request is given")
    void testTaskCreated_Success(){
        when(taskRepository.save(any(Task.class))).thenReturn(new Task());
        ResponseEntity<?> response = taskService.createTask(requestDTO);

        ApiResponse<?> body = (ApiResponse<?>) response.getBody();

        Assertions.assertNotNull(body);
        assertEquals("SUCCESS",body.getStatus());
        assertEquals("Task Created", body.getMessage());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should return failure when repository throws exception")
    void testTaskCreated_Failure(){
        when(taskRepository.save(any(Task.class)))
                .thenThrow(new RuntimeException("DB Failure"));

        ResponseEntity<?> response = taskService.createTask(requestDTO);
        ApiResponse<?> body = (ApiResponse<?>) response.getBody();

        Assertions.assertNotNull(body);
        assertEquals("FAILURE",body.getStatus());
        assertEquals("Task Not Created", body.getMessage());

        verify(taskRepository,times(1)).save(any(Task.class));
    }


//    fetchById Test

    @Test
    @DisplayName("Should return task when sending id ~ findById(UUID)")
    void testFindTaskById_Success(){
        UUID taskId = UUID.randomUUID();

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(new Task()));

        ResponseEntity<?> response = taskService.fetchById(taskId);
        ApiResponse<?> body = (ApiResponse<?>) response.getBody();

        Assertions.assertNotNull(body);
        assertEquals("TASK_FOUND",body.getCode());
    }


    @Test
    @DisplayName("When Task-id is null")
    void testFindById_Null(){
        ResponseEntity<?> response = taskService.fetchById(null);

        ApiResponse<?> body = (ApiResponse<?>) response.getBody();

        Assertions.assertNotNull(body);
        assertEquals("TASK_NOT_FOUND",body.getCode());
    }

//    fetchByTitle Test

    @Test
    @DisplayName("Find Task By the title")
    void testFindByTitle_Success(){
        when(taskRepository.findByTitle("threads-management")).thenReturn(Optional.of(new Task()));
        ResponseEntity<?> response = taskService.fetchByTitle("threads-management");

        ApiResponse<?> body = (ApiResponse<?>) response.getBody();
        Assertions.assertNotNull(body);
        assertEquals("TASK_FOUND",body.getCode());

        verify(taskRepository,times(1)).findByTitle("threads-management");
    }


    @Test
    @DisplayName("Find Task By Title not present")
    void testFindByTitle_Failure(){
        when(taskRepository.findByTitle("MISSING")).thenReturn(Optional.empty());
        ResponseEntity<?> response = taskService.fetchByTitle("MISSING");

        ApiResponse<?> body =(ApiResponse<?>) response.getBody();

        Assertions.assertNotNull(body);
        assertEquals("TASK_NOT_FOUND",body.getCode());

        verify(taskRepository,times(1)).findByTitle("MISSING");
    }

}
