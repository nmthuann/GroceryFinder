package com.nmt.groceryfinder.modules.inventories;


import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.CreateInventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.InventoryEntity;
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
    @Autowired
    public InventoryService(
            IInventoryRepository inventoryRepository,
            InventoryMapper inventoryMapper
    ) {
        super(inventoryRepository, inventoryMapper);
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    private InventoryEntity findInventoryByProductSkuId(Integer productSkuId) throws ModuleException {
        return inventoryRepository.findByProductSkuId(productSkuId)
                .orElseThrow(() -> new ModuleException(
                        "Inventory not found for Product SKU ID: " + productSkuId)
                );
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
            int soldQuantity
    ) throws ModuleException {
        InventoryEntity inventory = findInventoryByProductSkuId(productSkuId);
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
        InventoryEntity inventory = this.inventoryMapper.generateInventory(data, productSkuCreated);
        return Optional.ofNullable(this.inventoryMapper.toDto(this.inventoryRepository.save(inventory)));
    }

    @Override
    public Optional<InventoryDto> getOneByProductSkuId(Integer productSkuId) {
        Optional<InventoryEntity> findInventory =
                this.inventoryRepository.findByProductSkuId(productSkuId);
        return findInventory.map(entity -> this.inventoryMapper.toDto(entity));
    }
}
