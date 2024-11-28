package com.nmt.groceryfinder.modules.products.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_skus",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"slug"})})
@Getter
@Setter
public class ProductSkuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "barcode", nullable = false, length = 32)
    private String barcode;

    @Column(name = "sku_name", nullable = false)
    private String skuName;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(nullable = false)
    private Boolean status = true;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "sku_attributes", columnDefinition = "jsonb")
    private String skuAttributes;

    @OneToOne(mappedBy = "productSku", cascade = CascadeType.ALL)
    @JsonIgnore
    private SpuSkuMappingEntity spuSkuMapping;

    @OneToMany(mappedBy = "productSku")
    @JsonIgnore
    private List<InventoryEntity> inventories;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
