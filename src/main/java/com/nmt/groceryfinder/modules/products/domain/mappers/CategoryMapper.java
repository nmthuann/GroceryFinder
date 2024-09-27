package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.CategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateCategoryDto;
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


    public CategoryEntity generateCategory(CreateCategoryDto data, CategoryEntity parentNode) {
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setCategoryName(data.categoryName());
        newCategory.setDescription(data.description());
        newCategory.setCategoryUrl(data.categoryUrl());
        newCategory.setParentId((parentNode.getId().toString()));
        newCategory.setLeftValue(parentNode.getRightValue());
        newCategory.setRightValue(parentNode.getRightValue() + 1);
        return newCategory;
    }
}
