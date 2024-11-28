package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.ProductCardResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.SearchProductResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductSkuService extends IBaseService<Integer, ProductSkuDto> {
    Optional<ProductSkuDto> createOne(CreateProductSkuDto data);
    Optional<PriceDto> createPriceById(Integer id, CreatePriceDto data)throws ModuleException;
    Optional<InventoryDto> createInventoryById(Integer id, CreateInventoryDto data)throws ModuleException;
    List<PriceDto> getPricesByProductSkuId(Integer id);
    List<PriceDto>  getTop2PricesByProductSkuId(Integer id) throws ModuleException;
    ProductCardResponse getProductCardBySkuId(UUID spuId, Integer skuId) throws ModuleException;
    List<SearchProductResponse> searchSkusByName(String skuName);
}
