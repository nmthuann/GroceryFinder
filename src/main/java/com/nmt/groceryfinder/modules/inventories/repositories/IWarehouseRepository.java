package com.nmt.groceryfinder.modules.inventories.repositories;

import com.nmt.groceryfinder.modules.inventories.domain.model.entities.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@Repository
public interface IWarehouseRepository extends JpaRepository<WarehouseEntity, Integer> {
}
