package com.nmt.groceryfinder.modules.products.repositories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.CategoryEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, UUID> {
    Page<ProductEntity> findByCategory(CategoryEntity category, Pageable pageable);
    @Query("SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.product.id = :productId")
    Double findAverageRatingByProductId(@Param("productId") UUID productId);

    Optional<ProductEntity> findBySlug(String slug);


    @Query(value = """
        WITH RankedPrices AS (
            SELECT
                p.product_sku_id AS sku_id,
                p.unit_price,
                ROW_NUMBER() OVER (PARTITION BY p.product_sku_id ORDER BY p.begin_at DESC) AS rn
            FROM
                prices p
        ),
        PriceDetails AS (
            SELECT
                sku_id,
                MAX(CASE WHEN rn = 1 THEN unit_price END) AS new_price,
                MAX(CASE WHEN rn = 2 THEN unit_price END) AS old_price
            FROM
                RankedPrices
            GROUP BY
                sku_id
        )
        SELECT
            p.id AS id,
            sku.id AS skuId,
            p.product_name AS productName,
            sku.sku_name as SkuName,
            br.name AS brandName,
            sku.sku_description AS skuDescription,
            sku.image AS image,
            COALESCE(CAST(pd.old_price AS DOUBLE PRECISION), CAST(pd.new_price AS DOUBLE PRECISION)) AS oldPrice,
            COALESCE(CAST(pd.new_price AS DOUBLE PRECISION), CAST(pd.old_price AS DOUBLE PRECISION)) AS newPrice,
            COALESCE(AVG(CAST(re.rating AS DOUBLE PRECISION)), 5) AS rating
        FROM
            products p
            JOIN spu_sku_mapping ssm ON ssm.spu_id = p.id
            JOIN product_skus sku ON sku.id = ssm.sku_id
            LEFT JOIN brands br ON br.id = p.brand_id
            LEFT JOIN PriceDetails pd ON pd.sku_id = sku.id
            LEFT JOIN reviews re ON re.product_id = p.id
        WHERE
            p.category_id = :categoryId
        GROUP BY
            p.id, sku.id, br.name, pd.old_price, pd.new_price
        ORDER BY
            p.priority_sort ASC
        LIMIT :limit
        OFFSET :offset
        """, nativeQuery = true)
    List<Object[]> findProductDetailsByCategory(
            @Param("categoryId") Integer categoryId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );


    @Query(value = """
        WITH RankedPrices AS (
            SELECT
                p.product_sku_id AS sku_id,
                p.unit_price,
                ROW_NUMBER() OVER (PARTITION BY p.product_sku_id ORDER BY p.begin_at DESC) AS rn
            FROM
                prices p
        ),
        PriceDetails AS (
            SELECT
                sku_id,
                MAX(CASE WHEN rn = 1 THEN unit_price END) AS new_price,
                MAX(CASE WHEN rn = 2 THEN unit_price END) AS old_price
            FROM
                RankedPrices
            GROUP BY
                sku_id
        )
        SELECT
            p.id AS id,
            sku.id AS skuId,
            p.product_name AS productName,
              sku.sku_name as SkuName,
            br.name AS brandName,
            sku.sku_description AS skuDescription,
            sku.image AS image,
            COALESCE(CAST(pd.old_price AS DOUBLE PRECISION), CAST(pd.new_price AS DOUBLE PRECISION)) AS oldPrice,
            COALESCE(CAST(pd.new_price AS DOUBLE PRECISION), CAST(pd.old_price AS DOUBLE PRECISION)) AS newPrice,
            COALESCE(AVG(CAST(re.rating AS DOUBLE PRECISION)), 5) AS rating
        FROM
            products p
            JOIN spu_sku_mapping ssm ON ssm.spu_id = p.id
            JOIN product_skus sku ON sku.id = ssm.sku_id
            LEFT JOIN brands br ON br.id = p.brand_id
            LEFT JOIN PriceDetails pd ON pd.sku_id = sku.id
            LEFT JOIN reviews re ON re.product_id = p.id
        WHERE
            p.category_id = :categoryId
        GROUP BY
            p.id, sku.id, br.name, pd.old_price, pd.new_price
        ORDER BY
            p.priority_sort ASC,
            pd.new_price ASC
        LIMIT :limit
        OFFSET :offset
        """, nativeQuery = true)
    List<Object[]> findProductDetailsByCategoryOrderByNewPriceASC(
            @Param("categoryId") Integer categoryId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(value = """
        WITH RankedPrices AS (
            SELECT
                p.product_sku_id AS sku_id,
                p.unit_price,
                ROW_NUMBER() OVER (PARTITION BY p.product_sku_id ORDER BY p.begin_at DESC) AS rn
            FROM
                prices p
        ),
        PriceDetails AS (
            SELECT
                sku_id,
                MAX(CASE WHEN rn = 1 THEN unit_price END) AS new_price,
                MAX(CASE WHEN rn = 2 THEN unit_price END) AS old_price
            FROM
                RankedPrices
            GROUP BY
                sku_id
        )
        SELECT
            p.id AS id,
            sku.id AS skuId,
            p.product_name AS productName,
                sku.sku_name as SkuName,
            br.name AS brandName,
            sku.sku_description AS skuDescription,
            sku.image AS image,
            COALESCE(CAST(pd.old_price AS DOUBLE PRECISION), CAST(pd.new_price AS DOUBLE PRECISION)) AS oldPrice,
            COALESCE(CAST(pd.new_price AS DOUBLE PRECISION), CAST(pd.old_price AS DOUBLE PRECISION)) AS newPrice,
            COALESCE(AVG(CAST(re.rating AS DOUBLE PRECISION)), 5) AS rating
        FROM
            products p
            JOIN spu_sku_mapping ssm ON ssm.spu_id = p.id
            JOIN product_skus sku ON sku.id = ssm.sku_id
            LEFT JOIN brands br ON br.id = p.brand_id
            LEFT JOIN PriceDetails pd ON pd.sku_id = sku.id
            LEFT JOIN reviews re ON re.product_id = p.id
        WHERE
            p.category_id = :categoryId
        GROUP BY
            p.id, sku.id, br.name, pd.old_price, pd.new_price
        ORDER BY
            p.priority_sort ASC,
            pd.new_price DESC
        LIMIT :limit
        OFFSET :offset
        """, nativeQuery = true)
    List<Object[]> findProductDetailsByCategoryOrderByNewPriceDESC(
            @Param("categoryId") Integer categoryId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(value = """
        WITH RankedPrices AS (
            SELECT
                p.product_sku_id AS sku_id,
                p.unit_price,
                ROW_NUMBER() OVER (PARTITION BY p.product_sku_id ORDER BY p.begin_at DESC) AS rn
            FROM
                prices p
        ),
        PriceDetails AS (
            SELECT
                sku_id,
                MAX(CASE WHEN rn = 1 THEN unit_price END) AS new_price,
                MAX(CASE WHEN rn = 2 THEN unit_price END) AS old_price
            FROM
                RankedPrices
            GROUP BY
                sku_id
        )
        SELECT
            p.id AS id,
            sku.id AS skuId,
            p.product_name AS productName,
                sku.sku_name as SkuName,
            br.name AS brandName,
            sku.sku_description AS skuDescription,
            sku.image AS image,
            COALESCE(CAST(pd.old_price AS DOUBLE PRECISION), CAST(pd.new_price AS DOUBLE PRECISION)) AS oldPrice,
            COALESCE(CAST(pd.new_price AS DOUBLE PRECISION), CAST(pd.old_price AS DOUBLE PRECISION)) AS newPrice,
            COALESCE(AVG(CAST(re.rating AS DOUBLE PRECISION)), 5) AS rating
        FROM
            products p
            JOIN spu_sku_mapping ssm ON ssm.spu_id = p.id
            JOIN product_skus sku ON sku.id = ssm.sku_id
            LEFT JOIN brands br ON br.id = p.brand_id
            LEFT JOIN PriceDetails pd ON pd.sku_id = sku.id
            LEFT JOIN reviews re ON re.product_id = p.id
        WHERE
            p.category_id = :categoryId
        GROUP BY
            p.id, sku.id, br.name, pd.old_price, pd.new_price
        HAVING
            COALESCE(CAST(pd.new_price AS DOUBLE PRECISION), 0) < COALESCE(CAST(pd.old_price AS DOUBLE PRECISION), 0)
        ORDER BY
            p.priority_sort ASC,
            pd.new_price DESC
        LIMIT :limit
        OFFSET :offset
        """, nativeQuery = true)
    List<Object[]> findProductDetailsByCategoryHavingPrice(
            @Param("categoryId") Integer categoryId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    Page<ProductEntity> findByPrioritySort(Integer prioritySort, Pageable pageable);

    List<ProductEntity> findByProductNameOrderByPrioritySortAsc(String key);
}

