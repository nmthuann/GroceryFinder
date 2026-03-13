package com.nmt.groceryfinder.modules.products.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.dto.BrandDto;
import com.nmt.groceryfinder.modules.products.dto.CategoryDto;
import com.nmt.groceryfinder.modules.products.dto.ProductDto;
import com.nmt.groceryfinder.modules.products.requests.CreateProductDto;
import com.nmt.groceryfinder.modules.products.entities.BrandEntity;
import com.nmt.groceryfinder.modules.products.entities.CategoryEntity;
import com.nmt.groceryfinder.modules.products.entities.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
            CategoryDto category
    ){
        ProductDto productDto = new ProductDto();
        productDto.setId(productCreated.getId());
        productDto.setProductName(productCreated.getProductName());
        productDto.setDescription(productCreated.getDescription());
        productDto.setProductLine(productCreated.getProductLine());
        productDto.setProductSpecs(productCreated.getProductSpecs());
        productDto.setBrand(brand);
        productDto.setCategory(category);
        productDto.setIsDeleted(productCreated.getIsDeleted());
        productDto.setPrioritySort(productCreated.getPrioritySort());
        productDto.setCreatedAt(productCreated.getCreatedAt());
        productDto.setUpdatedAt(productCreated.getUpdatedAt());
        productDto.setCreatedBy(productCreated.getCreatedBy());
        productDto.setModifiedBy(productCreated.getModifiedBy());
        return productDto;
    }

    public ProductEntity generateEntity(
            CreateProductDto data,
            BrandEntity brand,
            CategoryEntity category
    ){
        ProductEntity product = new ProductEntity();
        product.setIsDeleted(false);
        product.setPrioritySort(data.prioritySort());
        product.setProductName(data.productName());
        product.setProductLine(data.productLine());
        product.setDescription(data.description());
        product.setProductSpecs(data.productSpecs());
        product.setBrand(brand);
        product.setCategory(category);
        return product;
    }
}
