package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductSkuRepository extends JpaRepository<ProductSkuEntity, Integer> {
    Optional<ProductSkuEntity> findBySlug(@Param("slug") String slug);
    Optional<ProductSkuEntity> findByBarcode(@Param("barcode") String barcode);
    @Query("" +
            "SELECT sku " +
            "FROM ProductSkuEntity sku " +
            "WHERE LOWER(sku.skuName) " +
            "LIKE LOWER(CONCAT('%', :skuName, '%')) "
    )
    Page<ProductSkuEntity> findBySkuNameContainingIgnoreCase(@Param("skuName") String skuName, Pageable pageable);
}