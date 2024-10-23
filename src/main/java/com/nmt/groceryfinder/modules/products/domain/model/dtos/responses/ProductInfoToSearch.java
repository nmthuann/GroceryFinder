package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/23/2024
 */
public record ProductInfoToSearch(
        UUID id,
        String slug,
        String productName,
        String categoryUrl
) {
}
