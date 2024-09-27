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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



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
    private final ImageMapper imageMapper;
    private final ISpuSkuMappingService spuSkuMappingService;
    private final IProductSkuService productSkuService;
    private  final ProductSkuMapper productSkuMapper;

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
            ImageMapper imageMapper,
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
        this.imageMapper = imageMapper;
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
        Optional<SpuSkuMappingDto> spuSkuMappingCreated =
                this.spuSkuMappingService.createOne(productCreated, productSkuEntity);
        return spuSkuMappingCreated;
    }

    @Override
    public GetProductDetailResponse getProductDetail(String identifier) throws ModuleException {
        return null;
    }

    @Override
    public Page<?> getAllPaginated(String option, Integer categoryId, Pageable pageable) throws ModuleException {
        return null;
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
    public Double getAverageRating(UUID productId) {
        Double averageRating = this.productRepository.findAverageRatingByProductId(productId);
        return averageRating != null ? averageRating : 5.0;
    }


    @Override
    public List<String> getProductNameListByKey(String key) {
        this.productRepository.findByProductNameOrderByPrioritySortAsc(key);
        return null;
    }
}
