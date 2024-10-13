package com.nmt.groceryfinder.modules.inventories.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/4/2024
 */
public record UpdateInventoryDto (
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        Date checkAt,
        @NotNull
        @Min(1)
        Integer stock,
        @NotNull
        Integer defective,
        @NotNull
        Integer sold,
        @NotNull
        @Min(1)
        Integer conversionFactor,
        @NotBlank
        String unit,
        @NotNull
        Boolean wholesale
) {
}
