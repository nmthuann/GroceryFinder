package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import org.hibernate.validator.constraints.URL;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/20/2024
 */
public record UpdateCategoryDto (

        String categoryName,
        @URL
        String categoryUrl,
        String description
){
}
