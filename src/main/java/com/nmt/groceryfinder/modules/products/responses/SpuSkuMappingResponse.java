package com.nmt.groceryfinder.modules.products.responses;

import com.nmt.groceryfinder.modules.products.dto.ProductDto;

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