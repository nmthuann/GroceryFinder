package com.nmt.groceryfinder.modules.inventories.domain.model.entities;


import com.nmt.groceryfinder.common.bases.AuditableEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
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

    @Column(name = "import_price", nullable = false, columnDefinition = "decimal default 0")
    private Double importPrice; // unit_price + vat + discount + ... => the price paid for one product

    @Column(name = "check_at", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date checkAt;

    @Column(name = "conversion_factor", nullable = false)
    private Integer conversionFactor;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Boolean wholesale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "warehouse_id",
            foreignKey = @ForeignKey(name = "fk_inventory_warehouse")
    )
    private WarehouseEntity warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_sku_id",
            foreignKey = @ForeignKey(name = "fk_inventory_product_sku")
    )
    private ProductSkuEntity productSku;
}
