package com.nmt.groceryfinder.modules.products.controllers;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateProductSkuDto;
import com.nmt.groceryfinder.modules.products.services.IProductSkuService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return inventoryDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                .orElseThrow(() -> new ModuleException("Inventory creation failed"));
    }

    @PostMapping("/{id}/prices")
    @LoggingInterceptor
    public ResponseEntity<?> createPriceById(
            @Parameter(description = "Product details to create", required = true)
            @RequestBody CreatePriceDto data,
            @PathVariable Integer id
    ) throws ModuleException {
        Optional<PriceDto> priceDto = this.productSkuService.createPriceById(id, data);
        return priceDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                .orElseThrow(() -> new ModuleException("Price creation failed"));
    }

    @GetMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<?> getOneById(
            @PathVariable Integer id
    ) {
        Optional<ProductSkuDto> productSkuDto = this.productSkuService.getOneById(id);
        if (productSkuDto.isPresent()) {
            return new ResponseEntity<>(productSkuDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product SKU not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<?> updateById(
            @PathVariable Integer id,
            @RequestBody ProductSkuDto data

    ) {
        Optional<ProductSkuDto> productSkuDto = Optional.ofNullable(this.productSkuService.updateOneById(id, data));
        if (productSkuDto.isPresent()) {
            return new ResponseEntity<>(productSkuDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product SKU not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/inventories")
    @LoggingInterceptor
    public ResponseEntity<InventoryDto> getInventoryBySkuId(
            @PathVariable Integer id
    ) throws ModuleException {
        Optional<InventoryDto> inventory = this.productSkuService.getInventoryBySkuId(id);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{id}/prices")
    @LoggingInterceptor
    public ResponseEntity<?> getPricesBySkuId(
            @PathVariable Integer id
    ) {
       List<PriceDto> prices = this.productSkuService.getPricesByProductSkuId(id);
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }
}
