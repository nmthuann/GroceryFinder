package com.nmt.groceryfinder.modules.inventories.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.WarehouseDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests.CreateWarehouseDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests.UpdateWarehouseDto;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
public interface IWarehouseService extends IBaseService<Integer, WarehouseDto> {
    WarehouseDto createOne(CreateWarehouseDto data) throws ModuleException;
    WarehouseDto updateOneById(Integer id, UpdateWarehouseDto data) throws ModuleException;
}
