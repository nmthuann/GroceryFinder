package com.nmt.groceryfinder.modules.products.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "images")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_image_product"))
    @JsonIgnore
    private ProductEntity product;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;
}
