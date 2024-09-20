package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Modifying // not use => InvalidDataAccessApiUsage
    @Query("UPDATE CategoryEntity c SET c.rightValue = c.rightValue + 2 WHERE c.rightValue >= :rightValue")
    void incrementRightValue(int rightValue);


    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Modifying
    @Query("UPDATE CategoryEntity c SET c.leftValue = c.leftValue + 2 WHERE c.leftValue > :rightValue")
    void incrementLeftValue(int rightValue);

    List<CategoryEntity> findByParentId(String parentId);

    @Modifying
    @Transactional
    @Query("UPDATE CategoryEntity c SET c.leftValue = c.leftValue - 2 WHERE c.leftValue > :leftValue")
    void updateLeftValuesAfterDelete(Integer leftValue);

    @Modifying
    @Transactional
    @Query("UPDATE CategoryEntity c SET c.rightValue = c.rightValue - 2 WHERE c.rightValue > :rightValue")
    void updateRightValuesAfterDelete(Integer rightValue);

    @Query("SELECT c " +
            "FROM CategoryEntity c " +
            "WHERE c.leftValue > :leftValue AND c.rightValue < :rightValue " +
            "ORDER BY c.leftValue")
    List<CategoryEntity> findChildrenByLeftAndRight (
            @Param("leftValue") Integer leftValue,
            @Param("rightValue") Integer rightValue
    );
    @Query("SELECT c FROM CategoryEntity c WHERE c.rightValue = c.leftValue + 1")
    List<CategoryEntity> findLeafCategories();

    @Query(value = """
        SELECT * FROM categories 
        WHERE left_value BETWEEN 
            (SELECT left_value FROM categories WHERE id = :parentId) 
            AND (SELECT right_value FROM categories WHERE id = :parentId)
            AND id != :parentId
        ORDER BY left_value
    """, nativeQuery = true)
    List<CategoryEntity> findChildCategories(@Param("parentId") Integer parentId);
}
