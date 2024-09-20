package com.nmt.groceryfinder.modules.inventories.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests.CreateInventoryDto;
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
    List<InventoryDto> getInventoriesByProductSkuId(ProductSkuEntity productSkuCreated);
    InventoryDto updateInventory(
            Integer productSkuId,
            Integer warehouseId,
            int soldQuantity
    ) throws ModuleException;
    Optional<InventoryDto> createOne(ProductSkuEntity productSkuCreated, CreateInventoryDto data);
    Optional<InventoryDto> getOneByProductSkuIdAndWarehouseId(Integer productSkuId, Integer warehouseId);
}
