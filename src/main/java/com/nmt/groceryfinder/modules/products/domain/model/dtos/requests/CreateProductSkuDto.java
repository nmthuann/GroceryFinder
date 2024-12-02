package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.*;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
public record CreateProductSkuDto (
    @NotNull(message = "Barcode is required")
    @Size(max = 32, message = "Barcode must be less than or equal to 32 characters")
    String barcode,
    @NotNull(message = "SKU Name is required")
    String skuName,
    @NotEmpty(message = "Sku Attributes cannot be empty")
    String skuAttributes,
    @NotEmpty(message = "Sku image cannot be empty")
    String image

) {
}
