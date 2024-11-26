package com.nmt.groceryfinder.modules.products.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nmt.groceryfinder.common.bases.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity extends AuditableEntity {
    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Column(name = "product_line", nullable = false)
    private String productLine;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "priority_sort", nullable = false)
    private Integer prioritySort = 0;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "product_specs", columnDefinition = "jsonb")
    private String productSpecs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "category_id",
            foreignKey = @ForeignKey(name = "fk_product_category")
    )
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "brand_id",
            foreignKey = @ForeignKey(name = "fk_product_brand")
    )
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "supplier_id",
            foreignKey = @ForeignKey(name = "fk_product_supplier")
    )
    private SupplierEntity supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ImageEntity> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SpuSkuMappingEntity> spuSkuMappings;
}
