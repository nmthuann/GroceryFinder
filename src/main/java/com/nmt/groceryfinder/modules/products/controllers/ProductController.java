package com.nmt.groceryfinder.modules.products.controllers;

import com.nmt.groceryfinder.common.messages.ProductMessages;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.RestErrorResponse;
import com.nmt.groceryfinder.exceptions.messages.ProductsModuleExceptionMessages;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.SpuSkuMappingDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.ProductCardResponse;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.responses.SpuSkuMappingResponse;
import com.nmt.groceryfinder.modules.products.services.IProductService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import com.nmt.groceryfinder.shared.ratelimit.RateLimiter;
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
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
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


    @GetMapping("/{id}")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<?> getOneById (
            @PathVariable UUID id
    ) throws ModuleException {
        Optional<?> product = this.productService.getOneById(id);
        return product.map(productDto -> new ResponseEntity<>(productDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
    public ResponseEntity<?> createOne(
            @Parameter(description = "Product details to create", required = true)
            @RequestBody CreateProductDto data
    ) throws ModuleException {
        Optional<ProductDto> productCreated = this.productService.createOne(data);
        return productCreated.map(productDto -> new ResponseEntity<>(productDto, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    @PutMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<ProductDto> updateOneById(
            @PathVariable UUID id,
            @RequestBody UpdateProductDto data
    ) throws ModuleException {
        Optional<ProductDto> productUpdated = this.productService.updateOneById(id, data);
        return productUpdated.map(productDto -> new ResponseEntity<>(productDto, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
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
    @RateLimiter
    public ResponseEntity<?> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String option,
            @RequestParam(required = false) Integer skuId,
            @RequestParam(required = false, defaultValue = "true") Boolean isPagination
    ) throws ModuleException {
        try{
            if(isPagination){
                PageRequest pageable = PageRequest.of(page, size, Sort.by("prioritySort").ascending());
                if(option == null) {
                    Page<ProductCardResponse> cardResponseList =
                            this.productService.getProductCardsByCategoryId(categoryId, pageable);
                    return new ResponseEntity<>(cardResponseList, HttpStatus.OK);
                }
                Page<?> products = this.productService.getAllPaginated(option, pageable);
                return new ResponseEntity<>(products, HttpStatus.OK);
            }
            else {
                if(skuId != null) {
                    SpuSkuMappingResponse getSpuSkuMapping =
                            this.productService.getSpuSkuMappingBySkuId(skuId);
                    return new ResponseEntity<>(getSpuSkuMapping, HttpStatus.OK);

                }

                List<ProductDto> products = this.productService.getAllByCategoryId(categoryId);
                return new ResponseEntity<>(products, HttpStatus.OK);
            }
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @Operation(
            summary = "Create SKUs for a product",
            description = "Add SKUs to a specific product using the provided details.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "SKUs successfully created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SpuSkuMappingDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    )
            }
    )
    @PostMapping("/{id}/skus")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<?> createSkuById(
            @Parameter(description = "List of SKUs to add", required = true)
            @RequestBody CreateProductSkuDto data,
            @Parameter(description = "ID of the product to which SKUs will be added", required = true)
            @PathVariable UUID id
    ) throws ModuleException {
        Optional<SpuSkuMappingDto> productSkuCreated = this.productService.createSkuById(id, data);
        if (productSkuCreated.isPresent()) {
            return new ResponseEntity<>(productSkuCreated.get(), HttpStatus.CREATED);
        }
        RestErrorResponse errorResponse = new RestErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ProductsModuleExceptionMessages.SKU_CREATION_BAD_REQUEST.getMessage(),
                ProductsModuleExceptionMessages.SKU_CREATION_FAILED.getMessage(),
                ProductsModuleExceptionMessages.SKU_CREATION_FAILED_DETAIL.getMessage(),
                ProductMessages.SKU_CREATION_DETAIL_MESSAGE.getMessage(),
                LocalDateTime.now(),
                "/api/products/" + id + "/skus"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/skus")
    @LoggingInterceptor
    @RateLimiter
    public ResponseEntity<?> getSpuSkuMapping(
            @PathVariable UUID id
    ) throws ModuleException {
        List<ProductCardResponse> skuDetailResponses = this.productService.getProductCardsBySpuId(id);
        return new ResponseEntity<>(skuDetailResponses, HttpStatus.OK);
    }

}