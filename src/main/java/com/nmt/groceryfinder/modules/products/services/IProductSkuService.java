package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;

import java.util.List;
import java.util.Optional;

public interface IProductSkuService extends IBaseService<Integer, ProductSkuDto> {
    Optional<ProductSkuDto> createOne(CreateProductSkuDto data);
    Optional<InventoryDto> createInventoryById(Integer id, CreateInventoryDto data)throws ModuleException;
    Optional<PriceDto> createPriceById(Integer id, CreatePriceDto data)throws ModuleException;
    Optional<InventoryDto> getInventoryBySkuId(Integer id)throws ModuleException;
    List<PriceDto> getPricesByProductSkuId(Integer id);

}
