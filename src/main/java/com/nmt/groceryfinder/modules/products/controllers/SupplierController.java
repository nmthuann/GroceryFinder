package com.nmt.groceryfinder.modules.products.controllers;

import com.nmt.groceryfinder.modules.products.domain.model.dtos.SupplierDto;
import com.nmt.groceryfinder.modules.products.services.ISupplierService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import com.nmt.groceryfinder.shared.ratelimit.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@RestController
@RequestMapping("/v1/suppliers")
@Tag(name = "Suppliers")
public class SupplierController {
    private final ISupplierService supplierService;

    @Autowired
    public SupplierController(
            ISupplierService supplierService
    ){
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<?> getOneById(@PathVariable Integer id) {
        Optional<SupplierDto> supplier = this.supplierService.getOneById(id);
        return supplier.map(supplierDto -> new ResponseEntity<>(supplierDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    @LoggingInterceptor
    public ResponseEntity<SupplierDto> createOne(@RequestBody SupplierDto data) {
        SupplierDto supplierCreated = this.supplierService.createOne(data);
        return new ResponseEntity<>(supplierCreated, HttpStatus.CREATED);
    }


    @GetMapping("")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<?> getAll()  {
        Iterable<SupplierDto> suppliers = this.supplierService.getAll();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<?> updateById(
            @PathVariable Integer id,
            @RequestBody SupplierDto data
    ) {
        Optional<SupplierDto> updatedSupplier = Optional.ofNullable(this.supplierService.updateOneById(id, data));
        return updatedSupplier
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
