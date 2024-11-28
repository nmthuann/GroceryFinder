package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;

import java.util.Optional;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/28/2024
 */
public interface IInventoryService extends IBaseService<Integer, InventoryDto> {
    Optional<InventoryDto> createOne(ProductSkuEntity productSkuCreated, CreateInventoryDto data) throws ModuleException;
    Optional<InventoryDto> getOneBySkuId(Integer skuId);
    Integer calculateTotalSoldBySkuId(Integer skuId);
}
