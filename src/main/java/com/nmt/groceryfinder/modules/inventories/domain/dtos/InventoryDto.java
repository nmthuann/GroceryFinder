package com.nmt.groceryfinder.modules.inventories.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nmt.groceryfinder.common.bases.AbstractBaseDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/2/2024
 */
@Getter
@Setter
public class InventoryDto extends AbstractBaseDto implements Serializable {
    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock must be zero or positive")
    private Integer stock;

    @NotNull(message = "Defective is required")
    @PositiveOrZero(message = "Defective must be zero or positive")
    private Integer defective;

    @NotNull(message = "Sold is required")
    @PositiveOrZero(message = "Sold must be zero or positive")
    private Integer sold;

    @NotNull(message = "Import price is required")
    @PositiveOrZero(message = "Import price must be zero or positive")
    private Double importPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date checkAt;

    @NotNull(message = "Conversion factor is required")
    @PositiveOrZero(message = "Conversion factor must be zero or positive")
    private Integer conversionFactor;

    @NotNull(message = "Unit is required")
    private String unit;

    @NotNull(message = "Wholesale is required")
    private Boolean wholesale;


    @JsonIgnore
    private ProductSkuDto productSku;
}
