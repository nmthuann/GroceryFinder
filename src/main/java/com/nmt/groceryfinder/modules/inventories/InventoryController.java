package com.nmt.groceryfinder.modules.inventories;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.UpdateInventoryDto;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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
    @LoggingInterceptor
    public ResponseEntity<InventoryDto> getInventoryBySkuId(
            @RequestParam(required = false) Integer productSkuId
    ) {
        Optional<InventoryDto> inventory =
                inventoryService.getOneByProductSkuId(productSkuId);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<InventoryDto> updateOneById(
            @PathVariable UUID id,
            @RequestBody UpdateInventoryDto data
    ) throws ModuleException {
        Optional<InventoryDto> inventory =
                Optional.ofNullable(inventoryService.updateInventory(id, data));
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
