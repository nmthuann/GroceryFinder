package com.nmt.groceryfinder.modules.inventories.services.impl;


import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.mappers.InventoryMapper;
import com.nmt.groceryfinder.modules.inventories.domain.mappers.WarehouseMapper;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.dtos.requests.CreateInventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.model.entities.InventoryEntity;
import com.nmt.groceryfinder.modules.inventories.repositories.IInventoryRepository;
import com.nmt.groceryfinder.modules.inventories.services.IInventoryService;
import com.nmt.groceryfinder.modules.inventories.services.IWarehouseService;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/2/2024
 */
@Service
public class InventoryService
        extends AbstractBaseService<InventoryEntity, UUID, InventoryDto>
        implements IInventoryService
{
    private final IInventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final IWarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;
    @Autowired
    public InventoryService(
            IInventoryRepository inventoryRepository,
            InventoryMapper inventoryMapper,
            IWarehouseService warehouseService,
            WarehouseMapper warehouseMapper
    ) {
        super(inventoryRepository, inventoryMapper);
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
        this.warehouseService = warehouseService;
        this.warehouseMapper = warehouseMapper;
    }

    @Override
    public List<InventoryDto> getInventoriesByProductSkuId(ProductSkuEntity productSkuCreated) {
        List<InventoryEntity> findInventoryEntities =
                this.inventoryRepository.findByProductSkuOrderByCreatedAt(productSkuCreated);
        return findInventoryEntities.stream().map(this.inventoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InventoryDto updateInventory(
            Integer productSkuId,
            Integer warehouseId,
            int soldQuantity
    ) throws ModuleException {
        InventoryEntity inventory =
                this.inventoryRepository.findByProductSkuIdAndWarehouseId(productSkuId, warehouseId)
                .orElseThrow(() -> new ModuleException("Inventory not found"));
        if (inventory.getStock() < soldQuantity) {
            throw new ModuleException("Not enough stock available");
        }
        inventory.setStock(inventory.getStock() - soldQuantity);
        inventory.setSold(inventory.getSold() + soldQuantity);
        InventoryEntity updatedInventory = this.inventoryRepository.save(inventory);
        return inventoryMapper.toDto(updatedInventory);
    }

    @Override
    public Optional<InventoryDto> createOne(ProductSkuEntity productSkuCreated, CreateInventoryDto data) {
        InventoryEntity inventory = new InventoryEntity();
        inventory.setSold(0);
        inventory.setDefective(0);
        inventory.setUnit(data.unit());
        inventory.setStock(data.stock());
        inventory.setCheckAt(data.checkAt());
        inventory.setConversionFactor(data.conversionFactor());
        inventory.setImportPrice(data.importPrice());
        inventory.setWholesale(data.wholesale());
        inventory.setProductSku(productSkuCreated);
        inventory.setWarehouse(
                this.warehouseMapper.toEntity(
                        this.warehouseService.getOneById(data.warehouseId()).get()
                )
        );
        return Optional.ofNullable(this.inventoryMapper.toDto(this.inventoryRepository.save(inventory)));
    }

    @Override
    public Optional<InventoryDto> getOneByProductSkuIdAndWarehouseId(Integer productSkuId, Integer warehouseId) {
        Optional<InventoryEntity> findInventory =
                this.inventoryRepository.findByProductSkuIdAndWarehouseId(productSkuId, warehouseId);
        return findInventory.map(entity -> this.inventoryMapper.toDto(entity));
    }
}
