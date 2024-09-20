package com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests;

import java.util.Date;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/6/2024
 */
public record CreateInventoryDto(
        Date checkAt,
        Double importPrice,
        Integer stock,
        Integer conversionFactor,
        String unit,
        Boolean wholesale,
        Integer warehouseId
) {
}
