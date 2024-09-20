package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    Page<ReviewEntity> findByProductOrderByCreatedAt(ProductEntity product, Pageable pageable);
}
