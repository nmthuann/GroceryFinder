package com.nmt.groceryfinder.modules.users.controllers;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.EmployeeDto;
import com.nmt.groceryfinder.modules.users.services.IEmployeeService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v2/employees")
public class EmployeeController {

    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<Optional<EmployeeDto>> getOneById(@PathVariable UUID id) {
        Optional<EmployeeDto> employeeDto = employeeService.getOneById(id);
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @PostMapping("")
    @LoggingInterceptor
    public  ResponseEntity<EmployeeDto> createOne(@RequestBody EmployeeDto data) {
        EmployeeDto employeeCreated = this.employeeService.createOne(data);
        return new ResponseEntity<>(employeeCreated, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<EmployeeDto> updateOneById(@PathVariable UUID id, @RequestBody EmployeeDto data) {
        EmployeeDto employeeUpdated = this.employeeService.updateOneById(id, data);
        return new ResponseEntity<>(employeeUpdated, HttpStatus.OK);
    }

    @GetMapping("")
    @LoggingInterceptor
    public ResponseEntity<Page<EmployeeDto>> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDto> employees = this.employeeService.getAllPaginated(pageable);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
