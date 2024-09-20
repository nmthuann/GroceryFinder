package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;

import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;


/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/11/2024
 */
public record ProductSkuResponse(
        Integer id,
        @NotNull(message = "SKU number is required")
        @NotEmpty(message = "SKU number cannot be empty")
        @Size(max = 32, message = "SKU number must be less than or equal to 32 characters")
        String skuNo,

        @NotNull(message = "Barcode is required")
        @NotEmpty(message = "Barcode cannot be empty")
        @Size(max = 32, message = "Barcode must be less than or equal to 32 characters")
        String barcode,

        @NotNull(message = "SKU model number is required")
        @NotEmpty(message = "SKU model number cannot be empty")
        @Size(max = 50, message = "SKU model number must be less than or equal to 50 characters")
        String skuModelName,

        String skuDescription,
        String image,
        InventoryDto inventory,
        List<PriceDto> prices
) {
}
