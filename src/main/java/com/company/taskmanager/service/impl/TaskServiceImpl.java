package com.company.taskmanager.service.impl;

import com.company.taskmanager.dto.request.TaskRequestDTO;
import com.company.taskmanager.model.Task;
import com.company.taskmanager.model.Users;
import com.company.taskmanager.repository.UsersRepository;
import com.company.taskmanager.service.TaskService;
import com.company.taskmanager.repository.TaskRepository;
import com.company.taskmanager.utility.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

    @Override
    public ResponseEntity<?> createTask(TaskRequestDTO requestDTO) {
        if(requestDTO!=null){
            Users assignedTo = usersRepository.findById(requestDTO.getAssignedTo().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Task task = Task.builder()
                    .title(requestDTO.getTitle())
                    .description(requestDTO.getDescription())
                    .assignedTo(assignedTo)
                    .completed(requestDTO.isCompleted())
                    .build();

            try {
                taskRepository.save(task);
            }
            catch (Exception e){
                log.error("exception while storing the data",e);
                return  ResponseUtil.failure("FAILED","Task Not Created");
            }
        }
        return ResponseUtil.success("CREATED","Task Created");
    }

    @Override
    public ResponseEntity<?> fetchById(UUID taskId) {

        if(taskId!=null){
            Task task =taskRepository.findById(taskId).orElseThrow();
            return ResponseUtil.success("TASK_FOUND", "task found", task);
        }
        return ResponseUtil.failure("TASK_NOT_FOUND","task not found please check the id");
    }

    @Override
    public ResponseEntity<?> fetchByTitle(String title) {

        Optional<Task> taskById = taskRepository.findByTitle(title);

        if(taskById.isEmpty())
            return  ResponseUtil.failure("TASK_NOT_FOUND","TASK NOT FOUND");
        return ResponseUtil.success("TASK_FOUND","TASK FOUND SUCCESSFULLY",taskById);

    }

    @Override
    public ResponseEntity<?> updateTask(UUID id, TaskRequestDTO requestDTO) {

        if(id!=null && requestDTO!=null){
            Optional<Task> existingTaskOptional = taskRepository.findById(id);
            Task updatedTask=null;

            if(existingTaskOptional.isPresent()){
                Task existingTask = existingTaskOptional.get();

                Task task = Task.builder()
                        .title(requestDTO.getTitle() != null ? requestDTO.getTitle() : existingTask.getTitle())
                        .description(requestDTO.getDescription() != null ? requestDTO.getDescription() : existingTask.getDescription())
                        .assignedTo(requestDTO.getAssignedTo() != null
                                ? usersRepository.findById(requestDTO.getAssignedTo().getId())
                                .orElseThrow(() -> new RuntimeException("User not found"))
                                : existingTask.getAssignedTo())
                        .completed(requestDTO.isCompleted() != existingTask.isCompleted()
                                ? requestDTO.isCompleted()
                                : existingTask.isCompleted())
                        .build();
                try {
                    updatedTask = taskRepository.save(task);
                }
                catch (Exception e){
                    log.error("Error while update to db: ",e);
                }
                return ResponseUtil.success("TASK_UPDATED","task updated successfully",updatedTask);
            }
        }
        return  ResponseUtil.failure("NULL_VALUE_NOT_ALLOWED","please enter values properly");
    }

    @Override
    public ResponseEntity<?> deleteById(UUID id) {

        if(id!=null) {
            try {
                taskRepository.deleteById(id);
            } catch (Exception e) {
                log.error("Exception while Deleting task :", e);
            }
            return ResponseUtil.success("TASK_DELETED", "Task Deleted successfully");
        }
        return  ResponseUtil.failure("NULL_ID","Null id is not allowed");
    }

    @Override
    public ResponseEntity<?> updateAssign(UUID id) {
        if(id!=null){

            Optional<Task> existingTask = taskRepository.findById(id);
            existingTask.ifPresent(updateTask ->{
                updateTask.setCompleted(true);
                taskRepository.save(updateTask);
            });

            return ResponseUtil.success("ASSIGN_UPDATED","assign updated successfully");
        }
        return null;
    }

}
