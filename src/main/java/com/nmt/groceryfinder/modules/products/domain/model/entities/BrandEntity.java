package com.nmt.groceryfinder.modules.products.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "brands")
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column
    private String description;

    @Column(name = "brand_business", nullable = false)
    private String brandBusiness;

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    private List<ProductEntity> products;
}
