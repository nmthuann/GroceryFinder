package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.util.Date;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/28/2024
 */
public record CreateInventoryDto(
        @NotNull(message = "Supplier ID is required")
        Integer supplierId,
        @NotNull(message = "Import Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Import Price must be greater than zero")
        @PositiveOrZero(message = "Import price must be zero or positive")
        Double importPrice,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        Date checkAt,
        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock must be zero or positive") //@Min(0)
        Integer stock,
        @NotBlank
        String unit
) {
}
