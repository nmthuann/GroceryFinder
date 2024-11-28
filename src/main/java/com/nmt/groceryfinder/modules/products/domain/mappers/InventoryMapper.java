package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/28/2024
 */
@Component
public class InventoryMapper extends AbstractModelMapper<InventoryEntity, Integer, InventoryDto> {
    @Autowired
    public InventoryMapper(ModelMapper modelMapper) {
        super(modelMapper, InventoryEntity.class, InventoryDto.class);
    }

    public InventoryEntity generateInventory(
            CreateInventoryDto data,
            ProductSkuEntity productSkuCreated,
            SupplierEntity supplier
    ){
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setDefective(0);
        inventoryEntity.setSold(0);
        inventoryEntity.setStock(data.stock());
        inventoryEntity.setImportPrice(data.importPrice());
        inventoryEntity.setUnit(data.unit());
        inventoryEntity.setCheckAt(data.checkAt());
        inventoryEntity.setSupplier(supplier);
        inventoryEntity.setProductSku(productSkuCreated);
        return inventoryEntity;
    }
}
