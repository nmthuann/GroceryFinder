package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/19/2024
 */
public record CreateProductDto(
        @NotNull(message = "Product name is required")
        @NotEmpty(message = "Product name cannot be empty")
        @Size(max = 255, message = "Product name must be less than or equal to 255 characters")
        String productName,
        @NotNull(message = "Product line is required")
        @NotEmpty(message = "Product line cannot be empty")
        String productLine,
        @NotEmpty(message = "Product line cannot be empty")
        String productSpecs,
        @NotNull(message = "Description is required")
        String description,
        @NotNull(message = "Category ID is required")
        Integer categoryId,
        @NotNull(message = "Brand ID is required")
        Integer brandId,
        @NotNull(message = "Supplier ID is required")
        Integer supplierId,
        @NotNull(message = "Category ID is required")
        Integer prioritySort
) {
}
