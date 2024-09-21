package com.nmt.groceryfinder.modules.inventories.domain.dtos;


/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/11/2024
 */
public record InventoryResponse(
        Integer stock,
        Double importPrice,
        Integer conversionFactor,
        String unit,
        Boolean wholesale
) {
}
