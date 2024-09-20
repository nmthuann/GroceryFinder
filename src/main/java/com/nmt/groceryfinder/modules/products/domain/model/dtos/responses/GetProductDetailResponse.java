package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;

import com.nmt.groceryfinder.modules.products.domain.model.dtos.BrandDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.CategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ImageDto;

import java.util.List;
import java.util.UUID;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/4/2024
 */
public record GetProductDetailResponse(
        UUID id,
        String productName,
        String slug,
        String productLine,
        String description,
        Boolean status,
        String productSpecs,
        CategoryDto category,
        BrandDto brand,
        List<ImageDto> images,
        List<ProductSkuResponse> productSkus
) {
}
