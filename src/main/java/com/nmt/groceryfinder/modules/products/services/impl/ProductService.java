package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.ProductsModuleExceptionMessages;
import com.nmt.groceryfinder.modules.inventories.domain.dtos.InventoryDto;
import com.nmt.groceryfinder.modules.products.domain.mappers.*;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.*;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.GetProductDetailResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.GetProductResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.ProductSkuResponse;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.products.repositories.IProductRepository;
import com.nmt.groceryfinder.modules.products.services.*;
import com.nmt.groceryfinder.utils.SlugUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.nmt.groceryfinder.utils.StringUtil.isUUID;


@Service
public class ProductService
        extends AbstractBaseService<ProductEntity, UUID, ProductDto>
        implements IProductService
{
    private final IProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ICategoryService categoryService;
    private final IBrandService brandService;
    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;
    private final IImageService imageService;
    private final ImageMapper imageMapper;
    private final ISpuSkuMappingService spuSkuMappingService;
    private final IProductSkuService productSkuService;
    private  final ProductSkuMapper productSkuMapper;
    private final IReviewService reviewService;


    @Autowired
    public ProductService(
            IProductRepository productRepository,
            ProductMapper productMapper,
            ICategoryService categoryService,
            IBrandService brandService,
            CategoryMapper categoryMapper,
            BrandMapper brandMapper,
            IImageService imageService,
            ImageMapper imageMapper,
            ISpuSkuMappingService spuSkuMappingService,
            IProductSkuService productSkuService,
            ProductSkuMapper productSkuMapper,
            IReviewService reviewService
    ) {
        super(productRepository, productMapper);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.brandMapper = brandMapper;
        this.categoryMapper = categoryMapper;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
        this.spuSkuMappingService = spuSkuMappingService;
        this.productSkuService = productSkuService;
        this.productSkuMapper = productSkuMapper;
        this.reviewService = reviewService;
    }

    @Override
    @Transactional
    public Optional<ProductDto> createOne(CreateProductDto data) throws ModuleException {
        CategoryDto findCategory = this.categoryService.getOneById(data.categoryId())
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_CATEGORY_NOT_FOUND.getMessage()));
        BrandDto findBrand = this.brandService.getOneById(data.brandId())
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.GET_BRAND_NOT_FOUND.getMessage()));
        ProductEntity newProduct = new ProductEntity();
        newProduct.setIsDeleted(false);
        newProduct.setPrioritySort(1);
        newProduct.setStatus(true);
        newProduct.setSlug(SlugUtil.createSlug(data.productName()));
        newProduct.setProductName(data.productName());
        newProduct.setProductLine(data.productLine());
        newProduct.setDescription(data.description());
        newProduct.setProductSpecs(data.productSpecs());
        newProduct.setBrand(brandMapper.toEntity(findBrand));
        newProduct.setCategory(categoryMapper.toEntity(findCategory));
        ProductEntity productCreated = this.productRepository.save(newProduct);
        List<ImageDto> imagesCreated =
                this.imageService.createImages(productCreated, data.images());
        return Optional.ofNullable(
                this.productMapper.mapForeignKeyToDto(
                        productCreated,
                        findBrand,
                        findCategory,
                        imagesCreated
                )
        );
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
    public List<ProductSkuDto> getSkusById(UUID id) throws ModuleException  {
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
    public ReviewDto createReviewById(UUID id, String email, CreateReviewDto data) {
        ProductEntity productCreated = this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        ReviewDto reviewCreated = this.reviewService.createOne(productCreated, email, data);
        return reviewCreated;
    }

    @Override
    public Page<ReviewDto> getReviewsById(UUID id, Pageable pageable) {
        ProductEntity productCreated = this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return this.reviewService.getReviewsByProduct(productCreated, pageable);
    }

    @Override
    public Double getAverageRating(UUID productId) {
        Double averageRating = this.productRepository.findAverageRatingByProductId(productId);
        return averageRating != null ? averageRating : 5.0;
    }



    @Override
    public Page<?> getAllPaginated(
            String option, Integer categoryId, Pageable pageable
    ) throws ModuleException {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        if (categoryId != null && "TopSale".equals(option)) {
            List<Object[]> rawResults =
                    this.productRepository.findProductDetailsByCategory(categoryId, limit, offset);
            List<GetProductResponse> productResponses = rawResults.stream()
                    .map(result -> new GetProductResponse(
                            ((UUID) result[0]),   // id
                            ((Number) result[1]).intValue(),    // skuId
                            (String) result[2],// productName
                            (String) result[3],
                            (String) result[4],                 // brandName
                            (String) result[5],                 // skuDescription
                            (String) result[6],                 // image
                            ((Double) result[7]).doubleValue(),  // oldPrice
                            ((Double) result[8]).doubleValue(),  // newPrice
                            ((Double) result[9]).doubleValue()   // rating
                    ))
                    .collect(Collectors.toList());

            long totalCount = this.productRepository.count();

            return new PageImpl<>(productResponses, pageable, totalCount);
        }
        if (categoryId != null && "PriceAcs".equals(option)) {
            List<Object[]> rawResults =
                    this.productRepository.findProductDetailsByCategoryOrderByNewPriceASC(categoryId, limit, offset);
            List<GetProductResponse> productResponses = rawResults.stream()
                    .map(result -> new GetProductResponse(
                            ((UUID) result[0]),   // id
                            ((Number) result[1]).intValue(),    // skuId
                            (String) result[2],// productName
                            (String) result[3],
                            (String) result[4],                 // brandName
                            (String) result[5],                 // skuDescription
                            (String) result[6],                 // image
                            ((Double) result[7]).doubleValue(),  // oldPrice
                            ((Double) result[8]).doubleValue(),  // newPrice
                            ((Double) result[9]).doubleValue()   // rating
                    ))
                    .collect(Collectors.toList());
            long totalCount = this.productRepository.count();
            return new PageImpl<>(productResponses, pageable, totalCount);
        }
        if (categoryId != null && "PriceDesc".equals(option)) {
            List<Object[]> rawResults =
                    this.productRepository.findProductDetailsByCategoryOrderByNewPriceDESC(categoryId, limit, offset);
            List<GetProductResponse> productResponses = rawResults.stream()
                    .map(result -> new GetProductResponse(
                            ((UUID) result[0]),   // id
                            ((Number) result[1]).intValue(),    // skuId
                            (String) result[2],// productName
                            (String) result[3],
                            (String) result[4],                 // brandName
                            (String) result[5],                 // skuDescription
                            (String) result[6],                 // image
                            ((Double) result[7]).doubleValue(),  // oldPrice
                            ((Double) result[8]).doubleValue(),  // newPrice
                            ((Double) result[9]).doubleValue()   // rating
                    ))
                    .collect(Collectors.toList());
            long totalCount = this.productRepository.count();
            return new PageImpl<>(productResponses, pageable, totalCount);
        }
        if (categoryId != null && "OnlyPromotion".equals(option)) {
            List<Object[]> rawResults =
                    this.productRepository.findProductDetailsByCategoryHavingPrice(categoryId, limit, offset);
            List<GetProductResponse> productResponses = rawResults.stream()
                    .map(result -> new GetProductResponse(
                            ((UUID) result[0]),   // id
                            ((Number) result[1]).intValue(),    // skuId
                            (String) result[2],// productName
                            (String) result[3],
                            (String) result[4],                 // brandName
                            (String) result[5],                 // skuDescription
                            (String) result[6],                 // image
                            ((Double) result[7]).doubleValue(),  // oldPrice
                            ((Double) result[8]).doubleValue(),  // newPrice
                            ((Double) result[9]).doubleValue()   // rating
                    ))
                    .collect(Collectors.toList());
            long totalCount = this.productRepository.count();
            return new PageImpl<>(productResponses, pageable, totalCount);
        }
        if(categoryId == null && option == null){
            return this.productRepository.findAll(pageable);
        }
        return Page.empty();
    }


    @Override
    public GetProductDetailResponse getProductDetail(String identifier) throws ModuleException  {
        ProductEntity productEntity;
        if (isUUID(identifier)) {
            UUID id = UUID.fromString(identifier);
            productEntity = this.productRepository.findById(id)
                    .orElseThrow(() -> new ModuleException(
                            ProductsModuleExceptionMessages.GET_PRODUCT_NOT_FOUND.getMessage()
                    ));
        } else {
            productEntity = this.productRepository.findBySlug("/"+identifier)
                    .orElseThrow(() -> new ModuleException(
                            ProductsModuleExceptionMessages.GET_PRODUCT_NOT_FOUND.getMessage()
                    ));
        }
        List<SpuSkuMappingDto> findSkus = this.spuSkuMappingService.getSkusByProductId(productEntity.getId());
        List<ProductSkuResponse> productSkus = new ArrayList<>();
        for(SpuSkuMappingDto findSku: findSkus){
            Optional<InventoryDto> findInventory =
                    this.productSkuService.getInventoryOnlineByProductSkuId(findSku.getProductSku().getId());
            List<PriceDto> findPrices =
                    this.productSkuService.getPricesByProductSku(findSku.getProductSku().getId());
            ProductSkuResponse productSkuResponse = new ProductSkuResponse(
                    findSku.getProductSku().getId(),
                    findSku.getProductSku().getSkuNo(),
                    findSku.getProductSku().getBarcode(),
                    findSku.getProductSku().getSkuName(),
                    findSku.getProductSku().getSkuDescription(),
                    findSku.getProductSku().getImage(),
                    findInventory.get(),
                    findPrices

            );
            productSkus.add(productSkuResponse);
        }
         return new GetProductDetailResponse(
                productEntity.getId(),
                productEntity.getProductName(),
                productEntity.getProductLine(),
                productEntity.getSlug(),
                productEntity.getDescription(),
                productEntity.getStatus(),
                productEntity.getProductSpecs(),
                categoryMapper.toDto(productEntity.getCategory()),
                brandMapper.toDto(productEntity.getBrand()),
                imageMapper.mapEntitiesToDto(productEntity.getImages()),
                 productSkus
        );
    }
}
