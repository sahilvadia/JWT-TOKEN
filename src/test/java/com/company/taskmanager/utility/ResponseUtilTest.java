package com.company.taskmanager.utility;

import com.company.taskmanager.dto.request.TaskRequestDTO;
import com.company.taskmanager.model.ApiResponse;
import com.company.taskmanager.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseUtilTest {

    @Test
    void successWithoutData_ShouldReturnSuccessResponse(){
        ResponseEntity<?> response = ResponseUtil.success("OK_200","Task Created");
        ApiResponse<?> body = (ApiResponse<?>) response.getBody();

        Assertions.assertNotNull(body);
        assertEquals("SUCCESS",body.getStatus());
        assertEquals("OK_200",body.getCode());
        assertEquals("Task Created",body.getMessage());
    }

    @Test
    void successWithData_ShouldReturnSuccessResponse(){

        TaskRequestDTO requestDTO = TaskRequestDTO.builder()
                .title("Test title")
                .description("Test description")
                .assignedTo("Tester")
                .completed(true)
                .build();

        ResponseEntity<?> response = ResponseUtil.success("OK_200","Task Created",requestDTO);

        ApiResponse<?> body = (ApiResponse<?>) response.getBody();

        assertEquals("SUCCESS",body.getStatus());
        assertEquals("OK_200",body.getCode());
        assertEquals(requestDTO,body.getData());
        assertEquals("Task Created",body.getMessage());
    }


    @Test
    void failureWithoutData_ShouldReturnFailureResponse(){
        ResponseEntity<?> response = ResponseUtil.failure("ERR_400","Bad Request");
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

        ApiResponse<?> body = (ApiResponse<?>) response.getBody();
        Assertions.assertNotNull(body);
        assertEquals("FAILURE",body.getStatus());
        assertEquals("ERR_400",body.getCode());
        assertEquals("Bad Request",body.getMessage());

    }
}
