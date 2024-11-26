package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record CreateCategoryDto(
        @NotNull(message = "ParentId is require!")
        Integer parentId,
        @NotNull(message = "CategoryName is require!")
        String categoryName,
        @NotNull(message = "Category Url is require!")
        String categoryUrl,
        @NotNull(message = "Description is require!")
        String description
) {
}
