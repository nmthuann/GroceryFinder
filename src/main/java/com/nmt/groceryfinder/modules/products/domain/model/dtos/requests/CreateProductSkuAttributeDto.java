package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.NotNull;


/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/31/2024
 */
public record CreateProductSkuAttributeDto(
        @NotNull
        String productSkuAttributeName

) {
}
