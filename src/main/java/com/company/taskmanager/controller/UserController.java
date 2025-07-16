package com.company.taskmanager.controller;


import com.company.taskmanager.dto.request.LoginDto;
import com.company.taskmanager.model.Users;
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
    public ResponseEntity<?> register(@RequestBody Users user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto user){return userService.login(user);}

    @GetMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam String name){return  userService.findByName(name);}
}
