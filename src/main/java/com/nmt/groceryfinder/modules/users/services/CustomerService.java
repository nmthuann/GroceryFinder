package com.nmt.groceryfinder.modules.users.services;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.modules.users.repositories.ICustomerRepository;
import com.nmt.groceryfinder.modules.users.dto.CustomerDto;
import com.nmt.groceryfinder.modules.users.entities.CustomerEntity;
import com.nmt.groceryfinder.modules.users.mappers.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService
        extends AbstractBaseService<CustomerEntity, Integer, CustomerDto>
        implements ICustomerService
{
    @Autowired
    public CustomerService(ICustomerRepository customerRepository, CustomerMapper customerMapper) {
        super(customerRepository, customerMapper);
    }
}
