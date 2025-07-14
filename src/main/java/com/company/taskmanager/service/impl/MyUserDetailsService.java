package com.company.taskmanager.service.impl;

import com.company.taskmanager.model.Task;
import com.company.taskmanager.model.UserPrincipal;
import com.company.taskmanager.model.Users;
import com.company.taskmanager.repository.TaskRepository;
import com.company.taskmanager.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findByUserName(username);
        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User Not found");
        }
        return new UserPrincipal(user);
    }
}
