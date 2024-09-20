package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import org.hibernate.validator.constraints.URL;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/19/2024
 */
public record CreateImageDto(
        @URL
        String imageUrl
) {
}
