package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.CategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateCategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateCategoryDto;

import java.util.List;

public interface ICategoryService extends IBaseService<Integer, CategoryDto> {

    /**
     * Creates a new category using the provided data.
     *
     * @param data DTO containing the details of the category to be created.
     * @return The created CategoryDto.
     * @throws ModuleException if there is an error during the creation process.
     */
    CategoryDto createOne(CreateCategoryDto data) throws ModuleException;

    /**
     * Updates an existing category by its ID using the provided data.
     *
     * @param id The ID of the category to be updated.
     * @param data DTO containing the new details of the category.
     * @return The updated CategoryDto.
     * @throws ModuleException if the category cannot be found or there is an error during the update.
     */
    CategoryDto updateOneById(Integer id, UpdateCategoryDto data) throws ModuleException;

    /**
     * Retrieves all categories that have the specified parent category ID.
     *
     * @param parentId The ID of the parent category to filter by.
     * @return A list of CategoryDto objects that are children of the specified parent category.
     */
    List<CategoryDto> getAllByParentId(String parentId);

    /**
     * Retrieves only child categories under the specified parent category.
     * This method may include additional logic to handle certain business cases.
     *
     * @param parentId The ID of the parent category to filter by.
     * @return A list of CategoryDto objects that are child categories.
     * @throws ModuleException if there is an error while fetching the child categories.
     */
    List<CategoryDto> getChildCategoriesByParentId(String parentId) throws ModuleException;

    /**
     * Retrieves all categories that are child categories (those with a parent).
     *
     * @return A list of CategoryDto objects representing all child categories.
     */
    List<CategoryDto> getChildCategories();

    /**
     * Retrieves all categories that are leaf categories (those with no children).
     *
     * @return An iterable collection of CategoryDto objects representing leaf categories.
     */
    Iterable<CategoryDto> getLeafCategories();


}
