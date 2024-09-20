package com.nmt.groceryfinder.modules.inventories.controllers;


import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.WarehouseDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests.CreateWarehouseDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests.UpdateWarehouseDto;
import com.nmt.groceryfinder.modules.inventories.services.IWarehouseService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/2/2024
 */
@RestController
@RequestMapping("/v1/warehouses")
public class WarehouseController {
    private final IWarehouseService warehouseService;

    @Autowired
    public WarehouseController(
            IWarehouseService warehouseService
    ){
        this.warehouseService = warehouseService;
    }

    @GetMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<Optional<WarehouseDto>> getOneById(@PathVariable Integer id) {
        Optional<WarehouseDto> warehouse = this.warehouseService.getOneById(id);
        return new ResponseEntity<>(warehouse, HttpStatus.OK);
    }

    @PostMapping("")
    @LoggingInterceptor
    public  ResponseEntity<WarehouseDto> createOne(@RequestBody CreateWarehouseDto data) throws ModuleException {
        WarehouseDto warehouseCreated = this.warehouseService.createOne(data);
            return new ResponseEntity<>(warehouseCreated, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<WarehouseDto> updateOneById(
            @PathVariable Integer id, @RequestBody UpdateWarehouseDto data
    ) throws ModuleException {
        WarehouseDto warehouseUpdated = this.warehouseService.updateOneById(id, data);
        return new ResponseEntity<>(warehouseUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<Void> deleteOneById(@PathVariable Integer id) {
        warehouseService.deleteOneById(id);
        try {
            this.warehouseService.getOneById(id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("")
    @LoggingInterceptor
    public ResponseEntity<?> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") Boolean isPagination
    ) {
        try {
            if (!isPagination) {
                Iterable<WarehouseDto> warehouses = this.warehouseService.getAll();
                return new ResponseEntity<>(warehouses, HttpStatus.OK);
            } else {
                Pageable pageable = PageRequest.of(page, size);
                Page<WarehouseDto> warehouses = this.warehouseService.getAllPaginated(pageable);
                return new ResponseEntity<>(warehouses, HttpStatus.OK);
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
}
