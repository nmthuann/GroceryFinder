package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;

import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;

import java.util.List;
import java.util.UUID;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/31/2024
 */
public record SpuSkuMappingResponse(
        UUID id,
        List<ProductSkuDto> productSkus
) {
}