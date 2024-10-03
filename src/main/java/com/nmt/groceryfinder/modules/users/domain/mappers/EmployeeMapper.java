package com.nmt.groceryfinder.modules.users.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.auth.dtos.requests.CreateEmployeeDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.EmployeeDto;
import com.nmt.groceryfinder.modules.users.domain.model.entities.EmployeeEntity;
import com.nmt.groceryfinder.modules.users.domain.model.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmployeeMapper extends AbstractModelMapper<EmployeeEntity, UUID, EmployeeDto> {

    @Autowired
    public EmployeeMapper(ModelMapper modelMapper) {
        super(modelMapper, EmployeeEntity.class, EmployeeDto.class);
    }

    public EmployeeEntity generateEntity(CreateEmployeeDto data, UserEntity userCreated){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setCccd(data.cccd());
        employeeEntity.setSalary(data.salary());
        employeeEntity.setWorkStatus(true);
        employeeEntity.setUser(userCreated);
        employeeEntity.setPosition(data.position());
        return employeeEntity;
    }
}
