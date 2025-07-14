package com.company.taskmanager.controller;


import com.company.taskmanager.dto.request.TaskRequestDTO;
import com.company.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequestDTO requestDTO){
        return taskService.createTask(requestDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") UUID id){
        return taskService.fetchById(id);
    }

    @GetMapping
    public ResponseEntity<?> findByTitle(@RequestParam String param){
        return taskService.fetchByTitle(param);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable UUID id, @RequestBody TaskRequestDTO taskRequestDTO){return taskService.updateTask(id,taskRequestDTO);}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id){return taskService.deleteById(id);}

    @PutMapping("/complete/{id}")
    public ResponseEntity<?> updateAssign(@PathVariable UUID id){
        return taskService.updateAssign(id);
    }

}

