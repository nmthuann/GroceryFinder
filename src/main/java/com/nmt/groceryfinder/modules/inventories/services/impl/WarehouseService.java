package com.nmt.groceryfinder.modules.inventories.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.InventoriesModuleExceptionMessages;
import com.nmt.groceryfinder.modules.inventories.domain.mappers.WarehouseMapper;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.WarehouseDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests.CreateWarehouseDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests.UpdateWarehouseDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.entities.WarehouseEntity;
import com.nmt.groceryfinder.modules.inventories.repositories.IWarehouseRepository;
import com.nmt.groceryfinder.modules.inventories.services.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@Service
public class WarehouseService
        extends AbstractBaseService<WarehouseEntity, Integer, WarehouseDto>
        implements IWarehouseService
{
    private final IWarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;


    @Autowired
    public WarehouseService(
            IWarehouseRepository warehouseRepository,
            WarehouseMapper warehouseMapper
    ) {
        super(warehouseRepository, warehouseMapper);
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }


    @Override
    public WarehouseDto createOne(CreateWarehouseDto data) throws ModuleException {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setWarehouseName(data.warehouseName());
        warehouseEntity.setStatus(true);
        warehouseEntity.setAddress(data.address());
        return this.warehouseMapper.toDto(this.warehouseRepository.save(warehouseEntity));
    }

    @Override
    public WarehouseDto updateOneById(Integer id, UpdateWarehouseDto data) throws ModuleException {
        Optional<WarehouseEntity> findWarehouse = this.warehouseRepository.findById(id);
        if (!findWarehouse.isPresent()) {
            throw new ModuleException(InventoriesModuleExceptionMessages.WAREHOUSE_NOT_FOUND.getMessage());
        }
        WarehouseEntity updateWarehouse = findWarehouse.get();
        if (data.warehouseName() != null) {
            updateWarehouse.setWarehouseName(data.warehouseName());
        }
        if (data.address() != null && data.address().equals(findWarehouse.get().getAddress())) {
            updateWarehouse.setAddress(data.address());
        }
        if (data.status() != null) {
            updateWarehouse.setStatus(data.status());
        }
        return this.warehouseMapper.toDto(this.warehouseRepository.save(updateWarehouse));
    }
}
