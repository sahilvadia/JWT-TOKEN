package com.company.taskmanager.service.impl;

import com.company.taskmanager.model.Employee;
import com.company.taskmanager.repository.EmployeeRepository;
import com.company.taskmanager.service.EmployeeService;
import com.company.taskmanager.utility.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<?> addEmployee(Employee employee) {

        if(employee!=null){
            employeeRepository.save(employee);
            ResponseUtil.success("EMP_ADDED","Employee Created");
        }
        return ResponseUtil.failure("FAIL-TO-ADD-EMPLOYEE","Employee not added");
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        return null;
    }
}
