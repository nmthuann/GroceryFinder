package com.nmt.groceryfinder.modules.products.repositories;


import com.nmt.groceryfinder.modules.products.domain.model.entities.PriceEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.PriceIdEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPriceRepository extends JpaRepository<PriceEntity, PriceIdEntity> {
    @Query("SELECT p FROM PriceEntity p WHERE p.id.productSku = :productSku ORDER BY p.id.beginAt DESC")
    List<PriceEntity> findTop2ByProductSkuOrderByBeginAtDesc(@Param("productSku") ProductSkuEntity productSku);
    @Query("SELECT p FROM PriceEntity p " +
            "WHERE p.id.productSku.id = :productSkuId AND p.id.beginAt <= :currentDate " +
            "ORDER BY p.id.beginAt DESC"
    )
    Optional<PriceEntity> findLatestPriceByProductSkuId(
            @Param("productSkuId") Integer productSkuId,
            @Param("currentDate") Date currentDate
    );

    List<PriceEntity> findAllByIdProductSkuId(@Param("productSkuId") Integer productSkuId);
    Optional<PriceEntity> findById(PriceIdEntity id);
}
