package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.dto.InventoryDto;
import com.nmt.groceryfinder.modules.products.dto.PriceDto;
import com.nmt.groceryfinder.modules.products.dto.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.requests.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.responses.ProductCardResponse;
import com.nmt.groceryfinder.modules.products.responses.ProductSkuResponse;
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
    ProductSkuResponse  getOneByBarcode(String barcode) throws ModuleException;
    ProductSkuResponse  getOneBySlug(String slug) throws ModuleException;
    List<ProductSkuDto> getSkusByName(String skuName);
}
