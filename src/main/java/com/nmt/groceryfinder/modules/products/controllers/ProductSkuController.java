package com.nmt.groceryfinder.modules.products.controllers;


import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.services.IProductSkuService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/7/2024
 */
@RestController
@RequestMapping("/v1/skus")
@Tag(name = "Product Skus")
public class ProductSkuController {
    private final IProductSkuService productSkuService;

    @Autowired
    public ProductSkuController (IProductSkuService productSkuService){
        this.productSkuService = productSkuService;
    }


    @PostMapping("/{id}/inventories")
    @LoggingInterceptor
    public ResponseEntity<?> createInventoryById(
            @Parameter(description = "Product details to create", required = true)
            @RequestBody CreateInventoryDto data,
            @PathVariable Integer id
    ) throws ModuleException {
        Optional<InventoryDto> inventoryDto = this.productSkuService.createInventoryById(id, data);
        return new ResponseEntity<>(inventoryDto.get(), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/prices")
    @LoggingInterceptor
    public ResponseEntity<?> createPriceById(
            @Parameter(description = "Product details to create", required = true)
            @RequestBody CreatePriceDto data,
            @PathVariable Integer id
    ) throws ModuleException {
        Optional<PriceDto> priceDto = this.productSkuService.createPriceById(id, data);
        return new ResponseEntity<>(priceDto.get(), HttpStatus.CREATED);
    }


    @PostMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<?> getOneById(
            @Parameter(description = "Product details to create", required = true)
            @PathVariable Integer id
    ) {
        Optional<ProductSkuDto> productSkuDto = this.productSkuService.getOneById(id);
        return new ResponseEntity<>(productSkuDto, HttpStatus.CREATED);
    }
}
