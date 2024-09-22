package com.nmt.groceryfinder.shared.elasticsearch;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/22/2024
 */

@Document(indexName = "products")
public class ProductDocument {

    @Id
    private String id;
    private String name;
    private Double price;

    // Getters and Setters
}
