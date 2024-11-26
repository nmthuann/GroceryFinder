package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.SpuSkuMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ISpuSkuMappingRepository extends JpaRepository<SpuSkuMappingEntity, Integer> {
    List<SpuSkuMappingEntity> findAllByProductId(UUID productId);
    SpuSkuMappingEntity findByProductSkuId(Integer productSkuId);
    SpuSkuMappingEntity findByProductSku(ProductSkuEntity productSku);
    @Query("SELECT s.product FROM SpuSkuMappingEntity s WHERE s.productSku.id = :skuId")
    ProductEntity findProductBySkuId(@Param("skuId") Integer skuId);
}
