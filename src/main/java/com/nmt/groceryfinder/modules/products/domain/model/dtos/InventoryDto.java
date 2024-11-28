package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/28/2024
 */
@Getter
@Setter
public class InventoryDto implements Serializable {
    private Integer id;
    private Double importPrice;
    private Integer sold;
    private Integer stock;
    private Integer defective;
    private String unit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedAt;
    private SupplierDto supplier;
    @JsonIgnore
    private  ProductSkuDto productSku;
}
