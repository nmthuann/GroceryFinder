package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.CategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.CategoryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper extends AbstractModelMapper<CategoryEntity, Integer, CategoryDto> {

    @Autowired
    public CategoryMapper(ModelMapper modelMapper) {
        super(modelMapper, CategoryEntity.class, CategoryDto.class);
    }
}
