package com.nmt.groceryfinder.modules.products.responses;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/29/2024
 */
public record ProductSkuResponse(
    Integer id,
    String slug,
    String barcode,
    String skuName,
    String image,
    Boolean status,
    String skuAttributes,
    Integer stock,
    Double latestPrice,
    Double oldPrice,
    String unit
) {
}
