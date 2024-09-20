package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IBrandRepository extends JpaRepository<BrandEntity, Integer> {
    @Query("SELECT br FROM BrandEntity br WHERE br.brandBusiness = :brandBusiness")
    List<BrandEntity> findByBrandBusiness(@Param("brandBusiness") String brandBusiness);
}
