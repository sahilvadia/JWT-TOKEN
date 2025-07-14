package com.company.taskmanager.service;

import com.company.taskmanager.dto.request.TaskRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface TaskService {

    ResponseEntity<?> createTask(TaskRequestDTO requestDTO);
    ResponseEntity<?> fetchById(UUID taskId);
    ResponseEntity<?> fetchByTitle(String title);
    ResponseEntity<?> updateTask(UUID id, TaskRequestDTO requestDTO);
    ResponseEntity<?> deleteById(UUID id);
    ResponseEntity<?> updateAssign(UUID id);
}
