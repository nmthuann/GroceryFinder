package com.nmt.groceryfinder.modules.inventories;


import com.nmt.groceryfinder.modules.inventories.domain.dtos.InventoryDto;
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
            @RequestParam(required = false) Integer productSkuId
    ) {
        Optional<InventoryDto> inventory =
                inventoryService.getOneByProductSkuId(productSkuId);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
