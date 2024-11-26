package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/20/2024
 */
public record UpdateCategoryDto (

        @NotNull
        String categoryName,
        @URL
        String categoryUrl,
        @NotNull
        String description
){
}
