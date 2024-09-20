package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.PriceIdEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;

import java.util.List;
import java.util.Optional;


public interface IPriceService extends IBaseService<PriceIdEntity, PriceDto> {
    Optional<PriceDto> createOne(ProductSkuEntity productSkuCreated, CreatePriceDto data);
    List<PriceDto> getByProductSkuId(ProductSkuEntity productSku);
    Optional<PriceDto> getLatestPriceByProductSkuId(Integer productSkuId);
}
