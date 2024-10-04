package com.nmt.groceryfinder.modules.inventories.domain;


import com.nmt.groceryfinder.common.bases.AuditableEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.users.domain.model.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "inventories")
public class InventoryEntity extends AuditableEntity {
    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer stock = 0; // available

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer defective = 0; // damaged

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer sold = 0; // sold

    @Column(name = "check_at", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date checkAt;

    @Column(name = "conversion_factor", nullable = false)
    private Integer conversionFactor;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Boolean wholesale;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "product_sku_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_inventory_product_sku")
    )
    private ProductSkuEntity productSku;
}
