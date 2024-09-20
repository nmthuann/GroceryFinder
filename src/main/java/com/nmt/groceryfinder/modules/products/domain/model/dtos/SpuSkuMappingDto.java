package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SpuSkuMappingDto implements Serializable {
    private Integer id;

    @NotNull(message = "Product SPU ID is required")
    private ProductDto product;

    @NotNull(message = "Product SKU ID is required")
    private ProductSkuDto productSku;
}
