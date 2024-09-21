package com.nmt.groceryfinder.modules.products.services.impl;


import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
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
    public Optional<InventoryDto> createInventoryById(
            Integer id,
            CreateInventoryDto data
    ) throws ModuleException {
        ProductSkuEntity productSkuCreated = this.productSkuRepository.findById(id)
                .orElseThrow(() -> new ModuleException("ProductSku not found"));
        return this.inventoryService.createOne(productSkuCreated, data);
    }

    @Override
    public Optional<PriceDto> createPriceById(Integer id, CreatePriceDto data) throws ModuleException {
        ProductSkuEntity productSkuCreated = this.productSkuRepository.findById(id)
                .orElseThrow(() -> new ModuleException("ProductSku not found"));
        return this.priceService.createOne(productSkuCreated, data);
    }

    @Transactional
    @Override
    public Optional<ProductSkuDto> createOne(CreateProductSkuDto data) {
        ProductSkuEntity productSkuEntity = new ProductSkuEntity();
        productSkuEntity.setSkuNo(data.skuNo());
        productSkuEntity.setBarcode(data.barcode());
        productSkuEntity.setSkuName(data.skuName());
        productSkuEntity.setSkuDescription(data.skuDescription());
        productSkuEntity.setStatus(true);
        ProductSkuEntity productSkuCreated = this.productSkuRepository.save(productSkuEntity);
        return Optional.ofNullable(this.productSkuMapper.toDto(productSkuCreated));
    }


    @Override
    public Double getUnitPriceByProductSkuId(Integer productSkuId) {
        Optional<PriceDto> findPrice = this.priceService.getLatestPriceByProductSkuId(productSkuId);
        return findPrice.get().getUnitPrice();
    }

    @Override
    public List<PriceDto> getPricesByProductSku(Integer productSkuId) throws ModuleException {
        ProductSkuEntity findProductSkuEntity = this.productSkuRepository.findById(productSkuId)
                .orElseThrow(() -> new ModuleException("ProductSku not found"));
        List<PriceDto> getPrices = this.priceService.getByProductSkuId(findProductSkuEntity);
        return getPrices;
    }


    @Override
    public Optional<InventoryDto> getInventoryOnlineByProductSkuId(Integer id) throws ModuleException {
        this.productSkuRepository.findById(id)
                .orElseThrow(() -> new ModuleException("ProductSku not found"));
        return this.inventoryService.getOneByProductSkuId(
                id
        );
    }
}