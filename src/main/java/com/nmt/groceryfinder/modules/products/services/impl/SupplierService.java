package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.modules.products.mappers.SupplierMapper;
import com.nmt.groceryfinder.modules.products.dto.SupplierDto;
import com.nmt.groceryfinder.modules.products.entities.SupplierEntity;
import com.nmt.groceryfinder.modules.products.repositories.ISupplierRepository;
import com.nmt.groceryfinder.modules.products.services.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@Service
public class SupplierService
        extends AbstractBaseService<SupplierEntity, Integer, SupplierDto>
        implements ISupplierService
{
    @Autowired
    public SupplierService(ISupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        super(supplierRepository, supplierMapper);
    }
}
