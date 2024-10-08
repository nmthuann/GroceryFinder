package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.ProductsModuleExceptionMessages;
import com.nmt.groceryfinder.modules.products.domain.mappers.*;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.*;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.GetProductDetailResponse;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.products.repositories.IProductRepository;
import com.nmt.groceryfinder.modules.products.services.*;
import com.nmt.groceryfinder.shared.elasticsearch.sync.SyncData;
import com.nmt.groceryfinder.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private final ISupplierService supplierService;
    private final SupplierMapper supplierMapper;
    private final IImageService imageService;
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
            ISupplierService supplierService,
            SupplierMapper supplierMapper,
            IImageService imageService,
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
        this.supplierService = supplierService;
        this.supplierMapper = supplierMapper;
        this.imageService = imageService;
        this.spuSkuMappingService = spuSkuMappingService;
        this.productSkuService = productSkuService;
        this.productSkuMapper = productSkuMapper;
    }

    private CategoryDto findCategoryById(Integer categoryId) throws ModuleException {
        return this.categoryService.getOneById(categoryId)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_CATEGORY_NOT_FOUND.getMessage()));
    }

    private BrandDto findBrandById(Integer brandId) throws ModuleException {
        return this.brandService.getOneById(brandId)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_BRAND_NOT_FOUND.getMessage()));
    }

    private SupplierDto findSupplierById(Integer supplierId) throws ModuleException {
        return this.supplierService.getOneById(supplierId)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_SUPPLIER_NOT_FOUND.getMessage()));
    }

    private ProductEntity createProductEntity(
            CreateProductDto data,
            BrandDto brand,
            CategoryDto category,
            SupplierDto supplier
    ) {
        return this.productMapper.generateEntity(
                data,
                brandMapper.toEntity(brand),
                categoryMapper.toEntity(category),
                supplierMapper.toEntity(supplier)
        );
    }

    @Override
    @Transactional
    @SyncData
    public Optional<ProductDto> createOne(CreateProductDto data) throws ModuleException {
        CategoryDto findCategory = findCategoryById(data.categoryId());
        BrandDto findBrand = findBrandById(data.brandId());
        SupplierDto findSupplier = findSupplierById(data.supplierId());
        ProductEntity newProduct = createProductEntity(data, findBrand, findCategory, findSupplier);
        ProductEntity productCreated = this.productRepository.save(newProduct);
        List<ImageDto> imagesCreated = this.imageService.createImages(productCreated, data.images());
        return Optional.ofNullable(this.productMapper.mapForeignKeyToDto(
                productCreated,
                findBrand,
                findCategory,
                findSupplier,
                imagesCreated
        ));
    }

    @Override
    @Transactional
    public Optional<SpuSkuMappingDto> createProductSkuById(
            UUID id,
            CreateProductSkuDto data
    ) throws ModuleException {
        ProductEntity productCreated = this.productRepository.findById(id)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_PRODUCT_NOT_FOUND.getMessage())
                );
        Optional<ProductSkuDto> productSkuCreated = this.productSkuService.createOne(data);
        ProductSkuEntity productSkuEntity = this.productSkuMapper.toEntity(productSkuCreated.get());
        return this.spuSkuMappingService.createOne(productCreated, productSkuEntity);
    }

    @Override
    public List<ProductSkuDto> getProductSkusById(UUID id) throws ModuleException  {
        ProductEntity productEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_PRODUCT_NOT_FOUND.getMessage()
                ));
        List<SpuSkuMappingDto> spuSkuMappingDtoList =
                this.spuSkuMappingService.getSkusByProductId(productEntity.getId());

        List<ProductSkuDto> productSkuDtoList = new ArrayList<>();
        for(SpuSkuMappingDto spuSkuMappingDto: spuSkuMappingDtoList){
            Optional<ProductSkuDto> productSkuDto =
                    this.productSkuService.getOneById(spuSkuMappingDto.getProductSku().getId());
            productSkuDtoList.add(productSkuDto.get());
        }
        return productSkuDtoList;
    }

    @Override
    public GetProductDetailResponse getProductDetail(String identifier) throws ModuleException {
        return null;
    }

    @Override
    public Page<ProductDto> getAllPaginated(Integer categoryId, String option, Pageable pageable) throws ModuleException {
        if (categoryId != null) {
            switch (option) {
                case "TopSale":
                    return this.productRepository.findAllByCategoryIdAndPrioritySort(categoryId, 1, pageable)
                            .map(this.productMapper::toDto);
                default:
                    return this.productRepository.findAllByCategoryId(categoryId, pageable)
                        .map(this.productMapper::toDto);
            }
        }

        if (option == null) {
            return this.productRepository.findAll(pageable)
                    .map(this.productMapper::toDto);
        }

        return Page.empty();
    }

    private Page<?> getProductsTopSale(Integer categoryId, Pageable pageable){
        return this.productRepository.findAllByCategoryIdAndPrioritySort(categoryId, 1, pageable);
    }

    @Override
    public List<String> searchProductsByKey(String key) {
        String decodedKey = UrlUtil.decodeUrl(key);
        List<ProductEntity> productEntities =
                this.productRepository.findByProductNameContainingIgnoreCase(decodedKey);
        return productEntities.stream()
                .map(ProductEntity::getProductName)
                .collect(Collectors.toList());
    }
}
