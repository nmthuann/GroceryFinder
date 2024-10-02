package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.CategoryEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, UUID> {
    Page<ProductEntity> findByCategory(CategoryEntity category, Pageable pageable);
    List<ProductEntity> findByProductNameOrderByPrioritySortAsc(String key);
}

