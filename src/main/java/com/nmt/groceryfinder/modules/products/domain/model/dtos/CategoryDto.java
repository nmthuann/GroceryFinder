package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoryDto implements Serializable {
    private Integer id;
    @NotNull(message = "Category name is required")
    private String categoryName;
    private String description;
    private String categoryUrl;
    @NotNull(message = "Left value is required")
    @Min(value = 1, message = "Left value must be at least 1")
    private Integer leftValue;
    @NotNull(message = "Right value is required")
    @Min(value = 2, message = "Right value must be at least 2")
    private Integer rightValue;
    @NotNull(message = "Parent ID is required")
    private String parentId;
}
