package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.SpuSkuMappingDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.ProductCardResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.SearchProductResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.SpuSkuMappingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface  IProductService extends IBaseService<UUID, ProductDto> {
    Optional<ProductDto> createOne(CreateProductDto data) throws ModuleException;
    Optional<ProductDto> updateOneById(UUID id, UpdateProductDto data) throws ModuleException;
    Optional<SpuSkuMappingDto> createSkuById(UUID id, CreateProductSkuDto data) throws ModuleException;
    List<ProductSkuDto> getSkusById(UUID id) throws ModuleException;
    List<ProductDto> getAllByCategoryId(Integer id);
    Page<ProductCardResponse> getProductCardsByCategoryId(Integer categoryId, Pageable pageable) throws ModuleException;
    Page<?> getAllPaginated(String option, Pageable pageable) throws ModuleException;
    List<ProductCardResponse> getProductCardsBySpuId (UUID spuId) throws ModuleException;
    SpuSkuMappingResponse getSpuSkuMappingBySkuId(Integer skuId) throws ModuleException;
    List<SearchProductResponse> searchProductByKey(String key);
}
