package com.company.taskmanager.controller;


import com.company.taskmanager.dto.request.LoginDto;
import com.company.taskmanager.model.Users;
import com.company.taskmanager.samrtlogger.LogRestCall;
import com.company.taskmanager.samrtlogger.TrackError;
import com.company.taskmanager.samrtlogger.TrackExecutionTime;
import com.company.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @TrackExecutionTime(tag = "REGISTER")
    public ResponseEntity<?> register(@RequestBody Users user){
        return userService.register(user);
    }

    @PostMapping("/login")
    @TrackExecutionTime(tag = "LOGIN")
    @LogRestCall(logInput = true, logOutPut = false)
    public ResponseEntity<?> login(@RequestBody LoginDto user){return userService.login(user);}

    @GetMapping("/findByName")
    @TrackError(module = "findByName")
    @TrackExecutionTime(tag = "findByName")
    @LogRestCall(logInput = true, logOutPut = false)
    public ResponseEntity<?> findByName(@RequestParam String name){return  userService.findByName(name);}
}
