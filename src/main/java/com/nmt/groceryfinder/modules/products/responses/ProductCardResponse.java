package com.nmt.groceryfinder.modules.products.responses;

import java.util.UUID;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/25/2024
 */
public record ProductCardResponse(
        Integer skuId,
        UUID spuId,
        String slug,
        String skuName,
        String skuAttributes,
        String image,
        Boolean status,
        Integer sold,
        Integer currentStock,
        Double latestPrice,
        Double oldPrice,
        String unit
) {
}
