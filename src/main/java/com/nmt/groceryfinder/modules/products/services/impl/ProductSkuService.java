package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.ProductsModuleExceptionMessages;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.CreateInventoryDto;
import com.nmt.groceryfinder.modules.inventories.IInventoryService;
import com.nmt.groceryfinder.modules.products.domain.mappers.ProductSkuMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.products.repositories.IProductSkuRepository;
import com.nmt.groceryfinder.modules.products.services.IPriceService;
import com.nmt.groceryfinder.modules.products.services.IProductSkuService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSkuService
        extends AbstractBaseService<ProductSkuEntity, Integer, ProductSkuDto>
        implements IProductSkuService
{
    private final IProductSkuRepository productSkuRepository;
    private final ProductSkuMapper productSkuMapper;
    private final IPriceService priceService;
    private final IInventoryService inventoryService;


    @Autowired
    public ProductSkuService(
            IProductSkuRepository productSkuRepository,
            ProductSkuMapper productSkuMapper,
            IPriceService priceService,
            IInventoryService inventoryService

    ) {
        super(productSkuRepository, productSkuMapper);
        this.productSkuRepository = productSkuRepository;
        this.productSkuMapper = productSkuMapper;
        this.priceService = priceService;
        this.inventoryService = inventoryService;
    }


    @Override
    public Optional<InventoryDto> createInventoryById(Integer id, CreateInventoryDto data) throws ModuleException {
        ProductSkuEntity productSku = findProductSkuById(id);
        return inventoryService.createOne(productSku, data);
    }

    @Override
    public Optional<PriceDto> createPriceById(Integer id, CreatePriceDto data) throws ModuleException {
        ProductSkuEntity productSku = findProductSkuById(id);
        return priceService.createOne(productSku, data);
    }

    @Override
    public Optional<InventoryDto> getInventoryBySkuId(Integer id) throws ModuleException {
        return this.inventoryService.getOneByProductSkuId(id);
    }

    @Override
    public List<PriceDto> getPricesByProductSkuId(Integer id) {
        return this.priceService.getPricesByProductSkuId(id);
    }

    @Override
    public List<PriceDto> getTop2PricesByProductSkuId(Integer id) throws ModuleException  {
        ProductSkuEntity productSkuCreated = this.findProductSkuById(id);
        return this.priceService.getTop2ByProductSku(productSkuCreated);
    }

    @Transactional
    @Override
    public Optional<ProductSkuDto> createOne(CreateProductSkuDto data) {
        ProductSkuEntity newProductSku = productSkuMapper.generateProductSku(data);
        ProductSkuEntity productSkuCreated = productSkuRepository.save(newProductSku);
        return Optional.ofNullable(productSkuMapper.toDto(productSkuCreated));
    }

    private ProductSkuEntity findProductSkuById(Integer id) throws ModuleException {
        return productSkuRepository.findById(id)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_PRODUCT_SKU_NOT_FOUND.getMessage() + " " + id
                ));
    }
}