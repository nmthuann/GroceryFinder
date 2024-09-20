package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.BrandDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.CategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ImageDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
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
        productDto.setStatus(productCreated.getStatus());
        productDto.setIsDeleted(productCreated.getIsDeleted());
        productDto.setPrioritySort(productCreated.getPrioritySort());
        productDto.setCreatedAt(productCreated.getCreatedAt());
        productDto.setUpdatedAt(productCreated.getUpdatedAt());
        productDto.setCreatedBy(productCreated.getCreatedBy());
        productDto.setModifiedBy(productCreated.getModifiedBy());
        productDto.setImages(images);
        return productDto;
    }
}
