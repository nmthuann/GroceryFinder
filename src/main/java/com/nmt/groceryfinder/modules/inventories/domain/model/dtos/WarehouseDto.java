package com.nmt.groceryfinder.modules.inventories.domain.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@Getter
@Setter
public class WarehouseDto {
    private Integer id;

    @NotBlank(message = "Warehouse name is required")
    @Size(max = 50, message = "Warehouse name must be less than 50 characters")
    private String warehouseName;

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Address must be less than 100 characters")
    private String address;

    @NotNull(message = "Status is required")
    private Boolean status;
}
