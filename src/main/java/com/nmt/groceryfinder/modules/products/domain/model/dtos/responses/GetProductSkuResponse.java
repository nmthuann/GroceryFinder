package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/4/2024
 */
public record GetProductSkuResponse(
        Integer id,
        String skuNo,
        String barcode,
        String skuModelName,
        String skuDescription,
        String image,
        Double newPrice,
        Double oldPrice,
        Integer stock,
        Integer conversionFactor,
        WarehouseDto warehouse,
        String unit
) {
}
