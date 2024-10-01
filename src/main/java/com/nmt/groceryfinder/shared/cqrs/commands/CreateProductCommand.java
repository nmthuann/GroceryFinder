package com.nmt.groceryfinder.shared.cqrs.commands;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
public record CreateProductCommand(
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
        Integer brandId
) {
}
