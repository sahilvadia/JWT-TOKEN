package com.company.taskmanager.utility;

import com.company.taskmanager.model.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


// Generic class that structured the response.

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(String code, String message){
        return ResponseEntity.ok(
          ApiResponse.<T>builder()
                  .status("SUCCESS")
                  .code(code)
                  .message(message)
                  .timestamp(LocalDateTime.now())
                  .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(String code, String message,T data){
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .status("SUCCESS")
                        .code(code)
                        .data(data)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> failure(String code, String message){
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .status("FAILURE")
                        .code(code)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> failure(String code, String message,T data){
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .status("FAILURE")
                        .code(code)
                        .message(message)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
