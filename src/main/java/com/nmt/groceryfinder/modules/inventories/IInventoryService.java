package com.nmt.groceryfinder.modules.inventories;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.CreateInventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.UpdateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/2/2024
 */
public interface IInventoryService extends IBaseService<UUID, InventoryDto> {
    InventoryDto updateInventory(
            UUID id,
            UpdateInventoryDto data
    ) throws ModuleException;
    Optional<InventoryDto> createOne(ProductSkuEntity productSkuCreated, CreateInventoryDto data);
    Optional<InventoryDto> getOneByProductSkuId(Integer productSkuId);
}
