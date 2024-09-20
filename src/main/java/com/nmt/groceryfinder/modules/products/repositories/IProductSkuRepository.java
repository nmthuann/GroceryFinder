package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductSkuRepository extends JpaRepository<ProductSkuEntity, Integer> {

}