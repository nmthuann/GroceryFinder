package com.nmt.groceryfinder.modules.inventories.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.WarehouseDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.entities.WarehouseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@Component
public class WarehouseMapper extends AbstractModelMapper<WarehouseEntity, Integer, WarehouseDto> {

    @Autowired
    public WarehouseMapper(ModelMapper modelMapper) {
        super(modelMapper, WarehouseEntity.class, WarehouseDto.class);
    }
}
