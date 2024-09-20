package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryDto(
        @NotNull(message = "ParentId is require!")
        Integer parentId,
        @NotEmpty(message = "CategoryName is require!")
        String categoryName,
        String categoryUrl,
        String description
) {
}
