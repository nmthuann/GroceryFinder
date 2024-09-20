package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;


import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/31/2024
 */
public record CreateSpuSkuMappingDto(
        ProductDto product,
        ProductSkuDto productSkuDto
) {
}
