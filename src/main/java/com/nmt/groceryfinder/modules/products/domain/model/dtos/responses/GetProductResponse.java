package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;


import java.util.UUID;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/31/2024
 */
public record GetProductResponse(
        UUID id,
        Integer skuId,
        String productName,
        String skuName,
        String brandName,
        String skuDescription,
        String image,
        Double oldPrice,
        Double newPrice,
        Double rating
) {
}