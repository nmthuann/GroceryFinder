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
import com.nmt.groceryfinder.utils.UrlUtil;
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

    private CategoryEntity findCategoryById(Integer id) throws ModuleException {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new ModuleException(
                        ProductsModuleExceptionMessages.CATEGORY_NOT_FOUND.getMessage()));
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
        CategoryEntity findParentNode = findCategoryById(data.parentId());
        categoryRepository.increaseRightValuesByTwo(findParentNode.getRightValue()); // transaction
        categoryRepository.increaseLeftValuesByTwo(findParentNode.getRightValue()); // transaction
        CategoryEntity newCategory = this.categoryMapper.generateCategory(data, findParentNode);
        return this.categoryMapper.toDto(this.categoryRepository.save(newCategory));
    }

    @Override
    public CategoryDto updateOneById(Integer id, UpdateCategoryDto data) throws ModuleException {
        Optional<CategoryEntity> findCategory = this.categoryRepository.findById(id);
        if (findCategory.isEmpty()) {
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
        return  categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getChildCategoriesByParentId(String parentId) throws ModuleException {
        CategoryEntity parentCategory = findCategoryById(Integer.parseInt(parentId));
        List<CategoryEntity> childCategories =
                this.categoryRepository.findChildrenByLeftAndRight (
                        parentCategory.getLeftValue(),
                        parentCategory.getRightValue()
                );
        return childCategories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getGroceryCategories() {
        List<CategoryEntity> findCategories
                = this.categoryRepository.findChildCategories(CategoryParentEnum.GROCERY_PARENT_ID.getCategoryId());
        return  findCategories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<CategoryDto> getLeafCategories() {
        return this.categoryRepository
                .findLeafCategories()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> searchCategoriesByKey(String key) {
        String decodedKey = UrlUtil.decodeUrl(key);
        List<CategoryDto> categories = this.getGroceryCategories();
        return categories
                .stream()
                .filter(categoryDto -> categoryDto
                        .getCategoryName()
                        .toLowerCase()
                        .contains(decodedKey.toLowerCase())
                ).toList();
    }

    @Override
    @Transactional
    public void deleteOneById(Integer id) {
        CategoryDto categoryToDelete;
        try {
            categoryToDelete = this.getOneById(id)
                    .orElseThrow(() -> new ModuleException(
                            ProductsModuleExceptionMessages.CATEGORY_NOT_FOUND.getMessage())
                    );
        } catch (ModuleException e) {
            throw new RuntimeException(e);
        }

        Integer leftValue = categoryToDelete.getLeftValue();
        Integer rightValue = categoryToDelete.getRightValue();

        super.deleteOneById(id);

        this.categoryRepository.updateLeftValuesAfterDelete(leftValue);
        this.categoryRepository.updateRightValuesAfterDelete(rightValue);
    }
}


