package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;

import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;

import java.util.List;
import java.util.UUID;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/10/2024
 */
public record CreateProductSkusResponse(
        UUID productId,
        List<CreateProductSkuDto> productSkus
) {
}
