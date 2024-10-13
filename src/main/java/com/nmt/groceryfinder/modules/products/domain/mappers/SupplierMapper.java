package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.SupplierDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.SupplierEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@Component
public class SupplierMapper extends AbstractModelMapper<SupplierEntity, Integer, SupplierDto> {

    @Autowired
    public SupplierMapper(ModelMapper modelMapper) {
        super(modelMapper, SupplierEntity.class, SupplierDto.class);
    }
}
