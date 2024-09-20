package com.nmt.groceryfinder.modules.users.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.auth.dtos.requests.CreateEmployeeDto;
import com.nmt.groceryfinder.modules.users.domain.mappers.EmployeeMapper;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.EmployeeDto;
import com.nmt.groceryfinder.modules.users.domain.model.entities.EmployeeEntity;
import com.nmt.groceryfinder.modules.users.domain.model.entities.UserEntity;
import com.nmt.groceryfinder.modules.users.repositories.IEmployeeRepository;
import com.nmt.groceryfinder.modules.users.services.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService
        extends AbstractBaseService<EmployeeEntity, UUID, EmployeeDto>
        implements IEmployeeService
{
    private final IEmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(
            IEmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper
    ) {
        super(employeeRepository, employeeMapper);
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDto createOne(
            UserEntity userCreated,
            CreateEmployeeDto data
    ) throws ModuleException {
        EmployeeEntity newEmployee = new EmployeeEntity();
        newEmployee.setCccd(data.cccd());
        newEmployee.setSalary(data.salary());
        newEmployee.setWorkStatus(true);
        newEmployee.setUser(userCreated);
        newEmployee.setPosition(data.positionName());
        return this.employeeMapper.toDto(this.employeeRepository.save(newEmployee));
    }

    @Override
    public Optional<EmployeeDto> getByEmail(String email) {
        Optional<EmployeeEntity> findEmployee = this.employeeRepository.findByUserEmail(email);
        return Optional.ofNullable(this.employeeMapper.toDto(findEmployee.get()));
    }
}
