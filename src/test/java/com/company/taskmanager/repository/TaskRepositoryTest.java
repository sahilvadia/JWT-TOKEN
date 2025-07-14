package com.company.taskmanager.repository;

import com.company.taskmanager.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//slice test ~ it work with h2 db in memory to check all the thing work properly with the db.
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("should Save task and findById")
    void testSaveAndFindById(){
        Task task = Task.builder()
                .title("Test Title")
                .description("This is test task description")
                .assignedTo("Tester")
                .completed(true)
                .build();

        Task saved = taskRepository.save(task);
        Optional<Task> existingTask = taskRepository.findById(saved.getId());

        assertThat(existingTask).isPresent();
        assertThat(existingTask.get().getTitle()).isEqualTo("Test Title");

    }


    @Test
    @DisplayName("Should Task FindByTitle")
    void testFindByTitle(){
        Task task = Task.builder()
                .title("Test title")
                .description("Test Description")
                .assignedTo("Debug")
                .completed(false)
                .build();

        taskRepository.saveAndFlush(task);
        Optional<Task> findByTitle = taskRepository.findByTitle(task.getTitle());

        assertThat(findByTitle).isPresent();
        assertThat(findByTitle.get().getTitle()).isEqualTo("Test title");
    }

    @Test
    @DisplayName("Should return empty when title does not match")
    void testFindByTitle_NotFound(){
        Optional<Task> result =  taskRepository.findByTitle("Non-existent Title");
        assertThat(result).isEmpty();
    }


    @Test
    @DisplayName("Should delete the task")
    void testDelete(){
        Task task = Task.builder()
                .title("Test title")
                .description("Test Description")
                .assignedTo("Debug")
                .completed(false)
                .build();


        Task saved = taskRepository.save(task);
        taskRepository.flush(); // need to flush to store
        UUID id = saved.getId();

        taskRepository.deleteById(id);

        assertThat(taskRepository.findById(id)).isEmpty();

    }

}
