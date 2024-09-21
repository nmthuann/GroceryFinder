package com.nmt.groceryfinder.modules.inventories;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.InventoryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/2/2024
 */
@Component
public class InventoryMapper extends AbstractModelMapper<InventoryEntity, UUID, InventoryDto> {
    /**
     * Constructor to initialize the mapper with required classes.
     * @param modelMapper the ModelMapper instance.
     */
    @Autowired
    public InventoryMapper(ModelMapper modelMapper) {
        super(modelMapper, InventoryEntity.class, InventoryDto.class);
    }

}