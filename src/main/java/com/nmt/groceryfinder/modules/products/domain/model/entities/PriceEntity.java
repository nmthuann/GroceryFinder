package com.nmt.groceryfinder.modules.products.domain.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "prices")
public class PriceEntity {
    @EmbeddedId
    private PriceIdEntity id;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "import_price", nullable = false)
    private Double importPrice; // unit_price + vat + discount + ... => the price paid for one product

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
