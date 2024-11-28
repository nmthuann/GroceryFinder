package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.mappers.InventoryMapper;
import com.nmt.groceryfinder.modules.products.domain.mappers.ProductSkuMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateInventoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.ProductCardResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.SearchProductResponse;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.products.repositories.IProductSkuRepository;
import com.nmt.groceryfinder.modules.products.services.IInventoryService;
import com.nmt.groceryfinder.modules.products.services.IPriceService;
import com.nmt.groceryfinder.modules.products.services.IProductSkuService;
import com.nmt.groceryfinder.utils.UrlUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductSkuService
        extends AbstractBaseService<ProductSkuEntity, Integer, ProductSkuDto>
        implements IProductSkuService
{
    private final IProductSkuRepository productSkuRepository;
    private final ProductSkuMapper productSkuMapper;
    private final IPriceService priceService;
    private final IInventoryService inventoryService;
    private final InventoryMapper inventoryMapper;


    @Autowired
    public ProductSkuService(
            IProductSkuRepository productSkuRepository,
            ProductSkuMapper productSkuMapper,
            IPriceService priceService,
            IInventoryService inventoryService,
            InventoryMapper inventoryMapper

    ) {
        super(productSkuRepository, productSkuMapper);
        this.productSkuRepository = productSkuRepository;
        this.productSkuMapper = productSkuMapper;
        this.priceService = priceService;
        this.inventoryService = inventoryService;
        this.inventoryMapper = inventoryMapper;
    }

    private ProductSkuEntity findProductSkuOrThrow(Integer id) throws ModuleException {
        return productSkuRepository.findById(id)
                .orElseThrow(() -> new ModuleException("Product SKU not found with id: " + id));
    }

    private InventoryDto findInventoryOrThrow(Integer skuId) throws ModuleException {
        return this.inventoryService.getOneBySkuId(skuId)
                .orElseThrow(() -> new ModuleException("Product SKU not found with id: " + skuId));
    }

    @Override
    public Optional<PriceDto> createPriceById(Integer id, CreatePriceDto data) throws ModuleException {
        ProductSkuEntity productSkuCreated = this.findProductSkuOrThrow(id);
        return priceService.createOne(productSkuCreated, data);
    }

    @Override
    public Optional<InventoryDto> createInventoryById(Integer id, CreateInventoryDto data) throws ModuleException {
        ProductSkuEntity productSkuCreated = this.findProductSkuOrThrow(id);
        return this.inventoryService.createOne(productSkuCreated, data);
    }


    @Override
    public List<PriceDto> getPricesByProductSkuId(Integer id) {
        return this.priceService.getPricesByProductSkuId(id);
    }

    @Override
    public List<PriceDto> getTop2PricesByProductSkuId(Integer id) throws ModuleException {
        ProductSkuEntity productSkuCreated = this.findProductSkuOrThrow(id);
        return this.priceService.getTop2ByProductSku(productSkuCreated);
    }

    @Override
    public ProductCardResponse getProductCardBySkuId(UUID spuId, Integer skuId) throws ModuleException {
        ProductSkuEntity productSkuCreated = this.findProductSkuOrThrow(skuId);
        List<PriceDto> prices = this.getTop2PricesByProductSkuId(skuId);
        Double latestPrice = prices.get(0).getUnitPrice();
        Double oldPrice = latestPrice;

        if(prices.size() == 2){
            latestPrice = prices.get(0).getUnitPrice();
            oldPrice = prices.get(1).getUnitPrice();
        }

        return new ProductCardResponse (
                productSkuCreated.getId(),
                spuId,
                productSkuCreated.getSlug(),
                productSkuCreated.getSkuName(),
                productSkuCreated.getImage(),
                productSkuCreated.getStatus(),
                this.inventoryService.calculateTotalSoldBySkuId(skuId),
                latestPrice,
                oldPrice
        );
    }

    @Override
    public List<SearchProductResponse> searchSkusByName(String skuName) {
        Pageable pageable = PageRequest.of(0, 5);
        String decodedKey = UrlUtil.decodeUrl(skuName);
        Page<ProductSkuEntity> productSkuDtoList =
                this.productSkuRepository.findBySkuNameContainingIgnoreCase(decodedKey, pageable);
        return productSkuDtoList.stream()
                .map(sku -> new SearchProductResponse(
                        sku.getId(),
                        sku.getSkuName(),
                        sku.getSlug()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<ProductSkuDto> createOne(CreateProductSkuDto data) {
        ProductSkuEntity newProductSku = productSkuMapper.generateProductSku(data);
        ProductSkuEntity productSkuCreated = productSkuRepository.save(newProductSku);
        return Optional.ofNullable(productSkuMapper.toDto(productSkuCreated));
    }
}