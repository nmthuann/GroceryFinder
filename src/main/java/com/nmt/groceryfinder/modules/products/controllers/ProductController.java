package com.nmt.groceryfinder.modules.products.controllers;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.SpuSkuMappingDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.GetProductDetailResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.SpuSkuMappingResponse;
import com.nmt.groceryfinder.modules.products.services.IProductService;
import com.nmt.groceryfinder.shared.elasticsearch.documents.ProductDocument;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/v1/products")
@Tag(name = "Products")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController (IProductService productService){
        this.productService = productService;
    }

    @Operation(
            summary = "Get a product by ID or slug",
            description = "Retrieve a product by its unique ID or slug.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found"
                    )
            }
    )
    @GetMapping("/{identifier}")
    @LoggingInterceptor
    public ResponseEntity<GetProductDetailResponse> getProductDetail(
            @Parameter(description = "ID or slug of the product to retrieve", required = true)
            @PathVariable String identifier
    ) throws ModuleException {
        GetProductDetailResponse product = this.productService.getProductDetail(identifier);;
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new product",
            description = "Create a new product with the provided details.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product successfully created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    )
            }
    )
    @PostMapping("")
    @LoggingInterceptor
    public ResponseEntity<ProductDto> createOne(
            @Parameter(description = "Product details to create", required = true)
            @RequestBody CreateProductDto data
    ) throws ModuleException {
        Optional<ProductDto> productCreated = this.productService.createOne(data);
        return new ResponseEntity<>(productCreated.get(), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all products by page",
            description = "Retrieve a paginated list of products.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of products retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @GetMapping("")
    @LoggingInterceptor
    public ResponseEntity<?> getAllPaginated(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String option,
            @Parameter(description = "Page number for pagination", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of products per page", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) throws ModuleException {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("prioritySort").ascending());
        // getCache
        Page<?> products = this.productService.getAllPaginated(option, categoryId, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(
            summary = "Create SKUs for a product",
            description = "Add SKUs to a specific product using the provided details.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "SKUs successfully created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SpuSkuMappingResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    )
            }
    )
    @PostMapping("/{id}/skus")
    @LoggingInterceptor
    public ResponseEntity<?> createSkuById(
            @Parameter(description = "List of SKUs to add", required = true)
            @RequestBody CreateProductSkuDto data,
            @Parameter(description = "ID of the product to which SKUs will be added", required = true)
            @PathVariable UUID id
    ) throws ModuleException {
        Optional<SpuSkuMappingDto> productSkuCreated = this.productService.createProductSkuById(id, data);
        return new ResponseEntity<>(productSkuCreated.get(), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get SKUs by product ID",
            description = "Retrieve SKUs associated with a specific product.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of SKUs retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found or no SKUs available"
                    )
            }
    )
    @GetMapping("/{id}/skus")
    @LoggingInterceptor
    public ResponseEntity<?> getSkusById(
            @Parameter(description = "ID of the product to retrieve SKUs for", required = true)
            @PathVariable UUID id
    ) throws ModuleException {
        List<ProductSkuDto> findSpuSkuMappingDtoList = this.productService.getProductSkusById(id);
        return new ResponseEntity<>(findSpuSkuMappingDtoList, HttpStatus.OK);
    }

    @GetMapping("/search")
    @LoggingInterceptor
    public ResponseEntity<?> searchProducts(@RequestParam String key){
        List<String> productNames = this.productService.searchProductsByKey(key);
        return ResponseEntity.ok(productNames);
    }


}