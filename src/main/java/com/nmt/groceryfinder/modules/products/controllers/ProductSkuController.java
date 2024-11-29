package com.nmt.groceryfinder.modules.products.controllers;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.ProductSkuResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.SearchProductResponse;
import com.nmt.groceryfinder.modules.products.services.IProductSkuService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import com.nmt.groceryfinder.shared.ratelimit.RateLimiter;
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

    @PostMapping("/{id}/inventories")
    @LoggingInterceptor
    public ResponseEntity<?> createInventoryById(
            @RequestBody CreateInventoryDto data,
            @PathVariable Integer id
    ) throws ModuleException {
        Optional<InventoryDto> inventoryDto = this.productSkuService.createInventoryById(id, data);
        return inventoryDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                .orElseThrow(() -> new ModuleException("InventoryDto creation failed"));
    }

    @GetMapping("/{id}")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<?> getOneById(
            @PathVariable Integer id
    ) {
        return this.productSkuService.getOneById(id)
                .<ResponseEntity<?>>map(
                        productSkuDto -> new ResponseEntity<>(productSkuDto, HttpStatus.OK)
                )
                .orElseGet(() -> new ResponseEntity<>("Product SKU not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<?> getSkus(
            @RequestParam String barcode
    ) {
        try {
            ProductSkuResponse productSkuResponse = this.productSkuService.getOneByBarcode(barcode);
            return new ResponseEntity<>(productSkuResponse, HttpStatus.OK);
        } catch (ModuleException e) {
            return new ResponseEntity<>("Product SKU not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
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

    @GetMapping("/{id}/prices")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<?> getPricesBySkuId(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = "false") Boolean isTop2
    ) throws ModuleException {
        if(isTop2){
            List<PriceDto> prices = this.productSkuService.getTop2PricesByProductSkuId(id);
            return new ResponseEntity<>(prices, HttpStatus.OK);
        }
       List<PriceDto> prices = this.productSkuService.getPricesByProductSkuId(id);
       return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @GetMapping("/search")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<?> searchProducts(@RequestParam String key){
        List<SearchProductResponse> productNames = this.productSkuService.searchSkusByName(key);
        return ResponseEntity.ok(productNames);
    }
}
