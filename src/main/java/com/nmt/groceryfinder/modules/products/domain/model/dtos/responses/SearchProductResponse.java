package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;

import java.util.UUID;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/23/2024
 */
public record SearchProductResponse(
        Integer id,
        String skuName,
        String slug,
        String categoryUrl
) {
}
