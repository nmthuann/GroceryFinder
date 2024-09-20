package com.nmt.groceryfinder.modules.products.domain.model.entities;

import com.nmt.groceryfinder.modules.users.domain.model.entities.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "rating", nullable = false)
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    @Column(name = "name", nullable = false)
    private String name;

    @Column()
    private String location; // vị trí sản phẩm

    @Column(name = "feedback", nullable = false, columnDefinition = "varchar")
    private String feedback;

    @Column(name = "image_url", columnDefinition = "varchar")
    private String imageUrl;

    @Column()
    private String link; // link tham chiếu

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "classification", columnDefinition = "jsonb")
    private String classification; // loại sản phẩm

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_review_product"))
    private ProductEntity product;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
