package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.SpuSkuMappingDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.GetProductDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface  IProductService extends IBaseService<UUID, ProductDto> {
    Optional<ProductDto> createOne(CreateProductDto data) throws ModuleException;
    Optional<SpuSkuMappingDto> createProductSkuById(UUID id, CreateProductSkuDto data) throws ModuleException;
    GetProductDetailResponse getProductDetail(String identifier) throws ModuleException;
    List<ProductSkuDto> getSkusById(UUID id) throws ModuleException;
    Page<?> getAllPaginated(String option, Integer categoryId, Pageable pageable) throws ModuleException;
    ReviewDto createReviewById (UUID id, String email, CreateReviewDto data);
    Page<ReviewDto> getReviewsById (UUID id, Pageable pageable);
    Double getAverageRating(UUID productId);
    List<String> getProductNameListByKey(String key);
}
