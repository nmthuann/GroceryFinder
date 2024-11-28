package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/28/2024
 */
@Repository
public interface IInventoryRepository extends JpaRepository<InventoryEntity, Integer> {
    Optional<InventoryEntity> findTopByProductSkuIdOrderByCreatedAtDesc(Integer productSkuId);
    @Query("SELECT SUM(i.sold) FROM InventoryEntity i WHERE i.productSku.id = :skuId")
    Integer getTotalSoldBySkuId(@Param("skuId") Integer skuId);
}
