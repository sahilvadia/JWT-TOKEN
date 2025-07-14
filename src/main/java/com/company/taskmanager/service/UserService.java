package com.company.taskmanager.service;

import com.company.taskmanager.dto.request.LoginDto;
import com.company.taskmanager.model.Users;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> register(Users user);
    ResponseEntity<?> login(LoginDto user);
}
