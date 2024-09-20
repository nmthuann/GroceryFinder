package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import java.util.Date;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/31/2024
 */
public record CreatePriceDto(
        Date beginAt,
        Double unitPrice
) {
}
