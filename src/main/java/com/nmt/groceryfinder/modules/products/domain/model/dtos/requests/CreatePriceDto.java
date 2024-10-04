package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/31/2024
 */
public record CreatePriceDto(
        @NotEmpty
        Date beginAt,
        @NotEmpty
        Double unitPrice,
        @NotEmpty
        Double importPrice
) {
}
