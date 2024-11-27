package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ProductSkuDto implements Serializable {
    private Integer id;
    private String slug;
    @NotNull(message = "Barcode is required")
    @NotEmpty(message = "Barcode cannot be empty")
    @Size(max = 32, message = "Barcode must be less than or equal to 32 characters")
    private String barcode;
    @NotNull(message = "SKU model name is required")
    @NotEmpty(message = "SKU model name cannot be empty")
    @Size(max = 50, message = "SKU model name must be less than or equal to 50 characters")
    private String skuName;
    private String image;
    @NotNull(message = "Status is required")
    private Boolean status;
    private String skuAttributes;
    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock must be zero or positive")
    private Integer stock;
    @NotNull(message = "Defective is required")
    @PositiveOrZero(message = "Defective must be zero or positive")
    private Integer defective;
    @NotNull(message = "Sold is required")
    @PositiveOrZero(message = "Sold must be zero or positive")
    private Integer sold;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date checkAt;
    @NotNull(message = "Unit is required")
    private String unit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updatedAt;
}
