package com.nmt.groceryfinder.modules.products.controllers;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.CategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateCategoryDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateCategoryDto;
import com.nmt.groceryfinder.modules.products.services.ICategoryService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/20/2024
 */
@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController (
            ICategoryService categoryService
    ){
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getOneById(@PathVariable Integer id) {
        Optional<CategoryDto> category = this.categoryService.getOneById(id);
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }

    @PostMapping("")
    @LoggingInterceptor
    public  ResponseEntity<CategoryDto> createOne(
            @RequestBody CreateCategoryDto data
    ) throws ModuleException {
        CategoryDto categoryCreated = this.categoryService.createOne(data);
        return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<CategoryDto> updateOneById(
            @PathVariable Integer id,
            @RequestBody UpdateCategoryDto data
    ) throws ModuleException {
        CategoryDto categoryUpdated = this.categoryService.updateOneById(id, data);
        return new ResponseEntity<>(categoryUpdated, HttpStatus.OK);
    }


    @GetMapping("")
    @LoggingInterceptor
    public ResponseEntity<?> getCategories(
            @RequestParam(required = false) String parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") Boolean isPagination,
            @RequestParam(required = false,  defaultValue = "false") Boolean isChild
    ) throws ModuleException{
        try {
            if (!isPagination) {
                if(isChild){
                    Iterable<CategoryDto> categories = this.categoryService.getLeafCategories();
                    return new ResponseEntity<>(categories, HttpStatus.OK);
                }
                Iterable<CategoryDto> categories = this.categoryService.getChildCategories();
                return new ResponseEntity<>(categories, HttpStatus.OK);
            }
            else if (parentId != null) {
                List<CategoryDto> categories = this.categoryService.getChildCategoriesByParentId(parentId);
                return new ResponseEntity<>(categories, HttpStatus.OK);
            } else {
                Pageable pageable = PageRequest.of(page, size);
                Page<CategoryDto> categories = this.categoryService.getAllPaginated(pageable);
                return new ResponseEntity<>(categories, HttpStatus.OK);
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @DeleteMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<Void> deleteOneById(@PathVariable Integer id){
        try {
            categoryService.deleteOneById(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
}