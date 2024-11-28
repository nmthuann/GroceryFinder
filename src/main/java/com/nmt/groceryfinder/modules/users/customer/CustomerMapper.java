package com.nmt.groceryfinder.modules.users.customer;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper extends AbstractModelMapper<CustomerEntity, Integer, CustomerDto> {

    @Autowired
    public CustomerMapper(ModelMapper modelMapper) {
        super(modelMapper, CustomerEntity.class, CustomerDto.class);
    }
}
