package com.nmt.groceryfinder.modules.products.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "spu_sku_mapping",
        uniqueConstraints = {@UniqueConstraint(name = "uk_spu_sku", columnNames = {"spu_id", "sku_id"})})
@Getter
@Setter
public class SpuSkuMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spu_id", foreignKey = @ForeignKey(name = "fk_spu_sku_mapping_product_spu"))
    private ProductEntity product;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_id", foreignKey = @ForeignKey(name = "fk_spu_sku_mapping_product_sku"))
    private ProductSkuEntity productSku;
}
