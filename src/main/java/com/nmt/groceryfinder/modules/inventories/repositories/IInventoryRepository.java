package com.nmt.groceryfinder.modules.inventories.repositories;


import com.nmt.groceryfinder.modules.inventories.domain.model.entities.InventoryEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/2/2024
 */
@Repository
public interface IInventoryRepository
        extends JpaRepository<InventoryEntity, UUID>
{
    List<InventoryEntity> findByProductSkuOrderByCreatedAt(ProductSkuEntity productSkuCreated);
    Optional<InventoryEntity> findByProductSkuIdAndWarehouseId(Integer productSkuId, Integer warehouseId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO inventory (" +
            "check_at, " +
            "import_price, " +
            "stock, " +
            "conversion_factor, " +
            "unit, " +
            "wholesale, " +
            "warehouse_id, " +
            "product_sku_id) " +
            "VALUES (:checkAt, :importPrice, :stock, :conversionFactor, :unit, :wholesale, :warehouseId, :productSkuId)",
            nativeQuery = true
    )
    default void insertInventory(
            @Param("checkAt") Date checkAt,
            @Param("importPrice") Double importPrice,
            @Param("stock") Integer stock,
            @Param("conversionFactor") Integer conversionFactor,
            @Param("unit") String unit,
            @Param("wholesale") Boolean wholesale,
            @Param("warehouseId") Integer warehouseId,
            @Param("productSkuId") Integer productSkuId
    ) {
    }

}
