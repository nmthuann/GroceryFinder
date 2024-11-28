package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;

import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;

import java.util.List;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/31/2024
 */
public record SpuSkuMappingResponse(
        ProductDto spu,
        List<ProductCardResponse> skus
) {
}