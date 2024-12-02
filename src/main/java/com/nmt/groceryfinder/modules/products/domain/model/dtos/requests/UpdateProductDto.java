package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/13/2024
 */
public record UpdateProductDto(
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
        @NotNull(message = "Status is required")
        Boolean status,
        @NotNull(message = "Deleted is required")
        Boolean isDeleted,
        @NotNull(message = "Category ID is required")
        Integer prioritySort
) {
}
