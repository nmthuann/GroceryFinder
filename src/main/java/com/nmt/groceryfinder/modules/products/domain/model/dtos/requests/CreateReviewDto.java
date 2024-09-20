package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/4/2024
 */
public record CreateReviewDto (
        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must not exceed 5")
        Integer rating,
        @NotNull(message = "Feedback is required")
        @NotEmpty(message = "Feedback cannot be empty")
        String feedback,

        @NotNull(message = "name is required")
        String name,

        String location,
        String imageUrl,
        @URL
        String link,
        @NotNull(message = "Classification is required")
        String classification
) {
}
