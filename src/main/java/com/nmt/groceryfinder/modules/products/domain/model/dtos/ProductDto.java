package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import com.nmt.groceryfinder.common.bases.AbstractBaseDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class    ProductDto extends AbstractBaseDto implements Serializable {

    String slug;

    @NotNull(message = "Product name is required")
    @NotEmpty(message = "Product name cannot be empty")
    @Size(max = 255, message = "Product name must be less than or equal to 255 characters")
    private String productName;

    @NotNull(message = "Product line is required")
    @NotEmpty(message = "Product line cannot be empty")
    private String productLine;

    private String description;

    @NotNull(message = "Status is required")
    private Boolean status;

    private Boolean isDeleted;

    private Integer prioritySort;

    private String productSpecs;
    private CategoryDto category;
    private BrandDto brand;
    private SupplierDto supplier;
    private List<ImageDto> images;
}
