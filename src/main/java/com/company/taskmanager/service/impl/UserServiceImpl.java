package com.company.taskmanager.service.impl;

import com.company.taskmanager.dto.request.LoginDto;
import com.company.taskmanager.model.Users;
import com.company.taskmanager.repository.UsersRepository;
import com.company.taskmanager.service.JwtService;
import com.company.taskmanager.service.UserService;
import com.company.taskmanager.utility.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return ResponseUtil.success("CREATED","user register");
    }

    @Override
    public ResponseEntity<?> login(@RequestBody LoginDto user){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return ResponseEntity.ok(jwtService.generateToken(user.getUserName()));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<?> findByName(String name) {
        return ResponseUtil.success("USER_FOUND","user found",usersRepository.findByUserName(name));
    }


}
