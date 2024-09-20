package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.SpuSkuMappingDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISpuSkuMappingService extends IBaseService<Integer, SpuSkuMappingDto> {
    Optional<SpuSkuMappingDto> createOne(ProductEntity productCreated, ProductSkuEntity productSkuCreated);
    List<SpuSkuMappingDto> getSkusByProductId(UUID id);
    SpuSkuMappingDto getOneByProductSkuId(ProductSkuEntity productSkuCreated);
    ProductEntity getProductByProductSkuId(Integer skuId);
}