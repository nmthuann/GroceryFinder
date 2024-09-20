package com.nmt.groceryfinder.modules.products.domain.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Embeddable
public class PriceIdEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_sku_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_price_product_sku"))
    private ProductSkuEntity productSku;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="begin_at")
    private Date beginAt;
}