package com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/4/2024
 */
public record CreateWarehouseDto(
        @NotBlank(message = "Warehouse name is required")
        @Size(max = 50, message = "Warehouse name must be less than 50 characters")
        String warehouseName,
        @NotBlank(message = "Address is required")
        @Size(max = 100, message = "Address must be less than 100 characters")
        String address
) {
}
