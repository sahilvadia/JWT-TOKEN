package com.company.taskmanager.service;

import com.company.taskmanager.model.Employee;
import org.springframework.http.ResponseEntity;


public interface EmployeeService {

    ResponseEntity<?> addEmployee(Employee employee);
    ResponseEntity<?> findById(Long id);
}
