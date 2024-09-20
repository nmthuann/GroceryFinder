package com.nmt.groceryfinder.modules.users.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.auth.dtos.requests.CreateEmployeeDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.EmployeeDto;
import com.nmt.groceryfinder.modules.users.domain.model.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface IEmployeeService extends IBaseService<UUID, EmployeeDto> {
    EmployeeDto createOne(UserEntity userCreated, CreateEmployeeDto data) throws ModuleException;
    Optional<EmployeeDto> getByEmail(String email);
}
