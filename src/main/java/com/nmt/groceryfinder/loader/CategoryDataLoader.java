package com.nmt.groceryfinder.loader;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nmt.groceryfinder.modules.products.domain.model.entities.CategoryEntity;
import com.nmt.groceryfinder.modules.products.repositories.ICategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.List;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/30/2024
 */
@Component
public class CategoryDataLoader  { //implements CommandLineRunner

    private final ObjectMapper objectMapper;
    private final ICategoryRepository categoryRepository;

    public CategoryDataLoader (ObjectMapper objectMapper, ICategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
    }

    public void run(String... args) throws Exception {
        if(categoryRepository.count() == 0) {
            String path = "/data/categories.json";

            try(InputStream inputStream = TypeReference.class.getResourceAsStream(path)){
                List<CategoryEntity> resCategoryEntities =
                        objectMapper.readValue(inputStream, List.class);
                this.categoryRepository.saveAll(resCategoryEntities);
            } catch (Exception exception){
                throw new RuntimeException("Failed to read JSON data", exception);
            }
        }
    }
}
