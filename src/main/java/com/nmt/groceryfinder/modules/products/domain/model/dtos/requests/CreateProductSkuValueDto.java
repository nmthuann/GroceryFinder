package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
public record CreateProductSkuValueDto(
        Integer productSkuAttributeId,
        String productSkuValue
){
}
