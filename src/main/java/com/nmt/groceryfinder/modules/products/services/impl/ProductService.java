package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.ProductsModuleExceptionMessages;
import com.nmt.groceryfinder.modules.products.domain.mappers.*;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.*;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.ProductCardResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.SearchProductResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.SpuSkuMappingResponse;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.products.repositories.IProductRepository;
import com.nmt.groceryfinder.modules.products.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductService
        extends AbstractBaseService<ProductEntity, UUID, ProductDto>
        implements IProductService
{
    private final IProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ICategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final IBrandService brandService;
    private final BrandMapper brandMapper;
    private final ISpuSkuMappingService spuSkuMappingService;
    private final IProductSkuService productSkuService;
    private final ProductSkuMapper productSkuMapper;

    @Autowired
    public ProductService(
            IProductRepository productRepository,
            ProductMapper productMapper,
            ICategoryService categoryService,
            CategoryMapper categoryMapper,
            IBrandService brandService,
            BrandMapper brandMapper,
            ISpuSkuMappingService spuSkuMappingService,
            IProductSkuService productSkuService,
            ProductSkuMapper productSkuMapper
    ) {
        super(productRepository, productMapper);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.brandService = brandService;
        this.brandMapper = brandMapper;
        this.spuSkuMappingService = spuSkuMappingService;
        this.productSkuService = productSkuService;
        this.productSkuMapper = productSkuMapper;
    }

    private CategoryDto findCategoryByIdOrElseThrow(Integer categoryId) throws ModuleException {
        return this.categoryService.getOneById(categoryId)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_CATEGORY_NOT_FOUND.getMessage()));
    }

    private BrandDto findBrandByIdOrElseThrow(Integer brandId) throws ModuleException {
        return this.brandService.getOneById(brandId)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_BRAND_NOT_FOUND.getMessage()));
    }

    private ProductSkuDto findSkuByIdOrElseThrow(Integer skuId) throws ModuleException {
        return this.productSkuService.getOneById(skuId)
                .orElseThrow(() -> new ModuleException("Product SKU not found for SKU ID: " + skuId));
    }

    private SpuSkuMappingDto findSpuBySkuIdOrElseThrow(Integer skuId) throws ModuleException {
        return this.spuSkuMappingService.getSpuBySkuId(skuId)
                .orElseThrow(() -> new ModuleException("Product SKU not found for SKU ID: " + skuId));
    }

    private ProductEntity findOneByIdOrElseThrow(UUID id) throws ModuleException {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_PRODUCT_NOT_FOUND.getMessage())
                );
    }


    private ProductEntity createProductEntity(
            CreateProductDto data,
            BrandDto brand,
            CategoryDto category
    ) {
        return this.productMapper.generateEntity(
                data,
                brandMapper.toEntity(brand),
                categoryMapper.toEntity(category)
        );
    }


    @Override
    @Transactional
    public Optional<ProductDto> createOne(CreateProductDto data) throws ModuleException {
        CategoryDto findCategory = findCategoryByIdOrElseThrow(data.categoryId());
        BrandDto findBrand = findBrandByIdOrElseThrow(data.brandId());
        ProductEntity newProduct = this.createProductEntity(data, findBrand, findCategory);
        ProductEntity productCreated = this.productRepository.save(newProduct);
        return Optional.ofNullable(this.productMapper.mapForeignKeyToDto(
                productCreated,
                findBrand,
                findCategory

        ));
    }


    @Override
    public Optional<ProductDto> updateOneById(UUID id, UpdateProductDto data) throws ModuleException {
        return Optional.empty();
    }


    @Override
    @Transactional
    public Optional<SpuSkuMappingDto> createSkuById (
            UUID id,
            CreateProductSkuDto data
    ) throws ModuleException {
        ProductEntity productCreated = this.findOneByIdOrElseThrow(id);
        Optional<ProductSkuDto> productSkuCreated = this.productSkuService.createOne(data);
        if (productSkuCreated.isPresent()) {
            ProductSkuEntity productSkuEntity = this.productSkuMapper.toEntity(productSkuCreated.get());
            return this.spuSkuMappingService.createOne(productCreated, productSkuEntity);
        }
        return Optional.empty();
    }

    @Override
    public List<ProductSkuDto> getSkusById(UUID id) throws ModuleException  {
        ProductEntity productEntity = this.findOneByIdOrElseThrow(id);
        List<SpuSkuMappingDto> spuSkuMappingDtoList =
                this.spuSkuMappingService.getSkusBySpuId(productEntity.getId());
        List<ProductSkuDto> productSkuDtoList = new ArrayList<>();
        for (SpuSkuMappingDto spuSkuMappingDto : spuSkuMappingDtoList) {
            Optional<ProductSkuDto> productSkuDto =
                    this.productSkuService.getOneById(spuSkuMappingDto.getProductSku().getId());
            productSkuDto.ifPresent(productSkuDtoList::add);
        }
        return productSkuDtoList;
    }

    @Override
    public List<ProductDto> getAllByCategoryId(Integer categoryId) {
        List<ProductEntity> productEntities = this.productRepository.findAllByCategoryId(categoryId);
        return productEntities
                .stream()
                .map(this.productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductCardResponse> getProductCardsByCategoryId (
            Integer categoryId,
            Pageable pageable
    ) throws ModuleException {
        Page<ProductEntity> findProductsByPage = this.productRepository.findAllByCategoryId(categoryId, pageable);
        List<ProductCardResponse> cardResponseList = new ArrayList<>();
        for(ProductEntity findProductEntity: findProductsByPage.stream().toList()){
            List<SpuSkuMappingDto> findSpuSkus = this.spuSkuMappingService.getSkusBySpuId(findProductEntity.getId());
            for(SpuSkuMappingDto findSpuSku: findSpuSkus) {
                ProductCardResponse cardResponse = this.productSkuService.getProductCardBySkuId(
                        findSpuSku.getProduct().getId(),
                        findSpuSku.getProductSku().getId()
                );
                cardResponseList.add(cardResponse);
            }
        }

        return new PageImpl<>(cardResponseList, pageable, findProductsByPage.getTotalElements());
    }


    @Override
    public Page<ProductDto> getAllPaginated(
            String option,
            Pageable pageable
    ) {
        if ("TopSale".equals(option)) {
            return this.productRepository.findAll(pageable)
                    .map(this.productMapper::toDto);
        }

        if (option == null) {
            return this.productRepository.findAll(pageable)
                    .map(this.productMapper::toDto);
        }
        return Page.empty();
    }

    @Override
    public List<ProductCardResponse> getProductCardsBySpuId(UUID spuId) throws ModuleException {
        ProductEntity findEntity = this.findOneByIdOrElseThrow(spuId);
        List<SpuSkuMappingDto> findSpuSkus = this.spuSkuMappingService.getSkusBySpuId(findEntity.getId());
        List<ProductCardResponse> cardResponseList = new ArrayList<>();
        for(SpuSkuMappingDto findSpuSku: findSpuSkus) {
            ProductCardResponse cardResponse = this.productSkuService.getProductCardBySkuId(
                    findSpuSku.getProduct().getId(),
                    findSpuSku.getProductSku().getId()
            );
            cardResponseList.add(cardResponse);
        }
        return cardResponseList;
    }

    @Override
    public SpuSkuMappingResponse getSpuSkuMappingBySkuId(Integer skuId)throws ModuleException {
        SpuSkuMappingDto findSpu = this.findSpuBySkuIdOrElseThrow(skuId);
        ProductEntity findEntity = this.findOneByIdOrElseThrow(findSpu.getProduct().getId());
        List<ProductCardResponse> findSkus = this.getProductCardsBySpuId(findEntity.getId());
        return new SpuSkuMappingResponse(
                this.productMapper.toDto(findEntity),
                findSkus
        );
    }

    @Override
    public List<SearchProductResponse> searchProductByKey(String key) {
        List<ProductSkuDto> productSkuDtoList = this.productSkuService.getSkusByName(key);
        return productSkuDtoList.stream()
                .flatMap(skuDto -> this.spuSkuMappingService.getSpuBySkuId(skuDto.getId())
                        .map(mappingDto -> new SearchProductResponse(
                                skuDto.getId(),
                                skuDto.getSkuName(),
                                skuDto.getSlug(),
                                mappingDto.getProduct().getCategory().getCategoryUrl()
                        ))
                        .stream() // Chuyển Optional thành Stream để xử lý chuỗi
                )
                .collect(Collectors.toList());
    }
}
