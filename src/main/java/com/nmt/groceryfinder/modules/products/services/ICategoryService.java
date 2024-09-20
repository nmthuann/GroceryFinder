package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.CategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateCategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateCategoryDto;

import java.util.List;

public interface ICategoryService extends IBaseService<Integer, CategoryDto> {
    CategoryDto createOne(CreateCategoryDto data) throws ModuleException;
    CategoryDto updateOneById(Integer id, UpdateCategoryDto data) throws ModuleException;
    List<CategoryDto> getAllByParentId(String parentId);
    List<CategoryDto> getChildCategoriesByParentId(String parentId);
    List<CategoryDto> getChildCategories();
    Iterable<CategoryDto> getLeafCategories();
}
