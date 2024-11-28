package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.ProductsModuleExceptionMessages;
import com.nmt.groceryfinder.modules.products.domain.mappers.InventoryMapper;
import com.nmt.groceryfinder.modules.products.domain.mappers.SupplierMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.SupplierDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.InventoryEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.PriceEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.SupplierEntity;
import com.nmt.groceryfinder.modules.products.repositories.IInventoryRepository;
import com.nmt.groceryfinder.modules.products.services.IInventoryService;
import com.nmt.groceryfinder.modules.products.services.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/28/2024
 */
@Service
public class InventoryService
        extends AbstractBaseService<InventoryEntity, Integer, InventoryDto>
        implements IInventoryService
{

    private final IInventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ISupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @Autowired
    public InventoryService(
            IInventoryRepository inventoryRepository,
            InventoryMapper inventoryMapper,
            ISupplierService supplierService,
            SupplierMapper supplierMapper
    ) {
        super(inventoryRepository, inventoryMapper);
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
        this.supplierService = supplierService;
        this.supplierMapper = supplierMapper;
    }

    private SupplierDto findSupplierById(Integer supplierId) throws ModuleException {
        return this.supplierService.getOneById(supplierId)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_SUPPLIER_NOT_FOUND.getMessage()));
    }


    @Override
    public Optional<InventoryDto> createOne(
            ProductSkuEntity productSkuCreated,
            CreateInventoryDto data
    ) throws ModuleException {
        SupplierEntity supplierEntity = this.supplierMapper.toEntity(findSupplierById(data.supplierId()));
        InventoryEntity inventoryEntity = this.inventoryMapper.generateInventory(
                data,
                productSkuCreated,
                supplierEntity
        );
        return Optional.ofNullable(this.inventoryMapper.toDto(this.inventoryRepository.save(inventoryEntity)));
    }

    @Override
    public Optional<InventoryDto> getOneBySkuId(Integer skuId) {
        return this.inventoryRepository.findTopByProductSkuIdOrderByCreatedAtDesc(skuId)
                .map(this.inventoryMapper::toDto);
    }

    @Override
    public Integer calculateTotalSoldBySkuId(Integer skuId) {
        return this.inventoryRepository.getTotalSoldBySkuId(skuId);
    }
}
