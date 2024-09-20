package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@Repository
public interface ISupplierRepository extends JpaRepository<SupplierEntity, Integer> {
}
