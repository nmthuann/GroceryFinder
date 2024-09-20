package com.nmt.groceryfinder.modules.users.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.EmployeeDto;
import com.nmt.groceryfinder.modules.users.domain.model.entities.EmployeeEntity;
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
}
