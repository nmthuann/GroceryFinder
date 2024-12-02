package com.nmt.groceryfinder.modules.products.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/28/2024
 */
@Getter
@Setter
@Entity
@Table(name = "inventories")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "import_price", nullable = false)
    private Double importPrice; // unit_price + vat + discount + ... => the price paid for one product

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer sold = 0;

    @Column( nullable = false, columnDefinition = "integer default 0")
    private Integer  stock  = 0; // available quantity

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer defective = 0;

    @Column(nullable = false)
    private String unit;

    @Column(name = "check_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_id", foreignKey = @ForeignKey(name = "fk_inventory_product_sku"))
    private ProductSkuEntity productSku;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "supplier_id",
            foreignKey = @ForeignKey(name = "fk_product_supplier")
    )
    private SupplierEntity supplier;

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
