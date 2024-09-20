package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IImageRepository extends JpaRepository<ImageEntity, UUID> {
}
