package com.nmt.groceryfinder.modules.products.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName = "ROOT";

    @Column(name = "description")
    private String description;

    @Column(name = "category_url")
    private String categoryUrl;

    /**
     * Kỹ thuật: Nested Set Model
     * (1, 'Electronics', 1, 10),
     * (2, 'Mobile Phones', 2, 5),
     * (3, 'Laptops', 6, 9),
     * (4, 'Smartphones', 3, 4),
     * (5, 'Gaming Laptops', 7, 8);
     */

    @Column(name = "left_value", nullable = false)
    private Integer leftValue = 1;

    @Column(name = "right_value", nullable = false)
    private Integer rightValue = 2;

    @Column(name = "parent_id", nullable = false)
    private String parentId;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProductEntity> products;

}
