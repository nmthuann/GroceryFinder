package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import com.nmt.groceryfinder.common.bases.AbstractBaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class ProductDto extends AbstractBaseDto implements Serializable {
    private String productName;
    private String productLine;
    private String description;
    private Boolean isDeleted;
    private Integer prioritySort;
    private String productSpecs;
    private CategoryDto category;
    private BrandDto brand;
}
