package com.nmt.groceryfinder.modules.users.customer.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.modules.users.customer.CustomerMapper;
import com.nmt.groceryfinder.modules.users.customer.CustomerDto;
import com.nmt.groceryfinder.modules.users.customer.CustomerEntity;
import com.nmt.groceryfinder.modules.users.customer.ICustomerRepository;
import com.nmt.groceryfinder.modules.users.customer.ICustomerService;
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
