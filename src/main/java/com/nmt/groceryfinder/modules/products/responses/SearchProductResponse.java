package com.nmt.groceryfinder.modules.products.responses;

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
