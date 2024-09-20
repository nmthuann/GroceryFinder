package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.common.enums.CategoryParentEnum;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.ProductsModuleExceptionMessages;
import com.nmt.groceryfinder.modules.products.domain.mappers.CategoryMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.CategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateCategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateCategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.CategoryEntity;
import com.nmt.groceryfinder.modules.products.repositories.ICategoryRepository;
import com.nmt.groceryfinder.modules.products.services.ICategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryService
        extends AbstractBaseService<CategoryEntity, Integer, CategoryDto>
        implements ICategoryService
{
    private final ICategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(
            ICategoryRepository categoryRepository,
            CategoryMapper categoryMapper
    ) {
        super(categoryRepository, categoryMapper);
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Creates a new category
     *
     * @param data Data transfer object containing category creation data
     * @return Created Category DTO
     * @throws ModuleException if parent category is not found
     */
    @Override
    @Transactional
    public CategoryDto createOne(CreateCategoryDto data) throws  ModuleException {
        Optional<CategoryEntity> findParentNode = this.categoryRepository.findById(data.parentId());
        if (!findParentNode.isPresent()){
            throw new ModuleException(ProductsModuleExceptionMessages.CATEGORY_PARENT_NOT_FOUND.getMessage());
        }
        categoryRepository.incrementRightValue(findParentNode.get().getRightValue()); // transaction
        categoryRepository.incrementLeftValue(findParentNode.get().getRightValue()); // transaction
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setCategoryName(data.categoryName());
        newCategory.setDescription(data.description());
        newCategory.setCategoryUrl(data.categoryUrl());
        newCategory.setParentId((findParentNode.get().getId().toString()));
        newCategory.setLeftValue(findParentNode.get().getRightValue());
        newCategory.setRightValue(findParentNode.get().getRightValue() + 1);
        return this.categoryMapper.toDto(this.categoryRepository.save(newCategory));
    }

    @Override
    public CategoryDto updateOneById(Integer id, UpdateCategoryDto data) throws ModuleException {
        Optional<CategoryEntity> findCategory = this.categoryRepository.findById(id);
        if (!findCategory.isPresent()) {
            throw new ModuleException(ProductsModuleExceptionMessages.CATEGORY_NOT_FOUND.getMessage());
        }
        CategoryEntity updateCategory = findCategory.get();
        if (data.categoryName() != null) {
            updateCategory.setCategoryName(data.categoryName());
        }
        if (data.description() != null) {
            updateCategory.setDescription(data.description());
        }
        if (data.categoryUrl() != null) {
            updateCategory.setCategoryUrl(data.categoryUrl());
        }
        return this.categoryMapper.toDto(this.categoryRepository.save(updateCategory));
    }

    @Override
    public List<CategoryDto> getAllByParentId(String parentId) {
        List<CategoryEntity> categories = this.categoryRepository.findByParentId(parentId);
        return  StreamSupport.stream(categories.spliterator(), false)
                .map(entity -> categoryMapper.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getChildCategoriesByParentId(String parentId) {
        Optional<CategoryEntity> parentCategory = this.categoryRepository.findById(Integer.parseInt(parentId));
        if (!parentCategory.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ProductsModuleExceptionMessages.CATEGORY_NOT_FOUND.getMessage()
            );
        }
        List<CategoryEntity> childCategories = this.categoryRepository.findChildrenByLeftAndRight (
                        parentCategory.get().getLeftValue(),
                        parentCategory.get().getRightValue()
                );

        return StreamSupport.stream(childCategories.spliterator(), false)
                .map(entity -> categoryMapper.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getChildCategories() {
        List<CategoryEntity> findCategories
                = this.categoryRepository.findChildCategories(CategoryParentEnum.GROCERY_PARENT_ID.getCategoryId());
        return  StreamSupport.stream(findCategories.spliterator(), false)
                .map(entity -> categoryMapper.toDto(entity))
                .collect(Collectors.toList());
    }



    @Override
    public Iterable<CategoryDto> getLeafCategories() {
        return StreamSupport.stream(
                this.categoryRepository
                        .findLeafCategories()
                        .spliterator(), false)
                .map(entity -> categoryMapper.toDto(entity))
                .collect(Collectors.toList());
    }



    @Override
    public void deleteOneById(Integer id) {
        Optional<CategoryEntity> findCategoryToDelete = this.categoryRepository.findById(id);
        if (!findCategoryToDelete.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ProductsModuleExceptionMessages.CATEGORY_NOT_FOUND.getMessage()
            );
        }
        CategoryEntity category = findCategoryToDelete.get();
        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ProductsModuleExceptionMessages.CATEGORY_NOT_DELETE.getMessage()
            );
        }
        Integer leftValue = findCategoryToDelete.get().getLeftValue();
        Integer rightValue = findCategoryToDelete.get().getRightValue();
        this.categoryRepository.deleteById(id);
        this.categoryRepository.updateLeftValuesAfterDelete(leftValue);
        this.categoryRepository.updateRightValuesAfterDelete(rightValue);
    }
}
