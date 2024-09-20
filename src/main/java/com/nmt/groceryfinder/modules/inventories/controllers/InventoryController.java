package com.nmt.groceryfinder.modules.inventories.controllers;


import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.services.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/inventories")
public class InventoryController {
    private final IInventoryService inventoryService;
    @Autowired
    public InventoryController(
            IInventoryService inventoryService
    ){
        this.inventoryService = inventoryService;
    }

    @GetMapping("")
    public ResponseEntity<InventoryDto> getInventoryBySkuAndWarehouse(
            @RequestParam(required = false) Integer productSkuId,
            @RequestParam(required = false) Integer warehouseId
    ) {
        Optional<InventoryDto> inventory =
                inventoryService.getOneByProductSkuIdAndWarehouseId(productSkuId, warehouseId);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
