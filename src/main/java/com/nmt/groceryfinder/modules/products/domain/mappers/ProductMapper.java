package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.*;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.BrandEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.CategoryEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.SupplierEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductMapper
        extends AbstractModelMapper<ProductEntity, UUID, ProductDto>
{

    @Autowired
    public ProductMapper(ModelMapper modelMapper) {
        super(modelMapper, ProductEntity.class, ProductDto.class);
    }


    public ProductDto mapForeignKeyToDto(
            ProductEntity productCreated,
            BrandDto brand,
            CategoryDto category,
            SupplierDto supplier,
            List<ImageDto> images
    ){
        ProductDto productDto = new ProductDto();
        productDto.setId(productCreated.getId());
        productDto.setProductName(productCreated.getProductName());
        productDto.setDescription(productCreated.getDescription());
        productDto.setProductLine(productCreated.getProductLine());
        productDto.setProductSpecs(productCreated.getProductSpecs());
        productDto.setBrand(brand);
        productDto.setCategory(category);
        productDto.setSupplier(supplier);
        productDto.setIsDeleted(productCreated.getIsDeleted());
        productDto.setPrioritySort(productCreated.getPrioritySort());
        productDto.setCreatedAt(productCreated.getCreatedAt());
        productDto.setUpdatedAt(productCreated.getUpdatedAt());
        productDto.setCreatedBy(productCreated.getCreatedBy());
        productDto.setModifiedBy(productCreated.getModifiedBy());
        productDto.setImages(images);
        return productDto;
    }

    public ProductEntity generateEntity(
            CreateProductDto data,
            BrandEntity brand,
            CategoryEntity category,
            SupplierEntity supplier
    ){
        ProductEntity product = new ProductEntity();
        product.setIsDeleted(data.isDeleted());
        product.setPrioritySort(data.prioritySort());
        product.setProductName(data.productName());
        product.setProductLine(data.productLine());
        product.setDescription(data.description());
        product.setProductSpecs(data.productSpecs());
        product.setBrand(brand);
        product.setCategory(category);
        product.setSupplier(supplier);
        return product;
    }
}
