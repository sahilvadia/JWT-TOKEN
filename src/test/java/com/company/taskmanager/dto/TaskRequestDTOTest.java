package com.company.taskmanager.dto;

import com.company.taskmanager.dto.request.TaskRequestDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskRequestDTOTest {

    @Test
    void testBuilderAndGetters(){
        TaskRequestDTO testTaskRequest = TaskRequestDTO.builder()
                .title("Test title")
                .description("Test description")
                .assignedTo("Tester")
                .completed(true)
                .build();

        assertEquals("Test title",testTaskRequest.getTitle());
        assertEquals("Test description",testTaskRequest.getDescription());
        assertEquals("Tester",testTaskRequest.getAssignedTo());
        assertTrue(testTaskRequest.isCompleted());

    }

    @Test
    void testAllArgConstructor(){
        TaskRequestDTO dto = new TaskRequestDTO(
                "Title"
                ,"description"
                ,"assigned"
                ,true
                );

        assertEquals("Title",dto.getTitle());
    }

    @Test
    void testEqualsAndHashcode(){
        TaskRequestDTO dto1 = TaskRequestDTO.builder()
                .title("Test title")
                .description("Test description")
                .assignedTo("Tester")
                .completed(true)
                .build();

        TaskRequestDTO dto2 = TaskRequestDTO.builder()
                .title("Test title")
                .description("Test description")
                .assignedTo("Tester")
                .completed(true)
                .build();

        assertEquals(dto1,dto2);
        assertEquals(dto1.hashCode(),dto2.hashCode());
    }
}
