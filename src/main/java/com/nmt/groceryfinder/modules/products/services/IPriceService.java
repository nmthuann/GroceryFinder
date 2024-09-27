package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.PriceIdEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;

import java.util.List;
import java.util.Optional;


public interface IPriceService extends IBaseService<PriceIdEntity, PriceDto> {
    /**
     * Creates a new price entry for a specified product SKU.
     *
     * @param productSkuCreated The product SKU entity for which the price is being created.
     * @param data DTO containing the details of the price to be created.
     * @return An Optional containing the created PriceDto, or empty if the creation failed.
     */
    Optional<PriceDto> createOne(ProductSkuEntity productSkuCreated, CreatePriceDto data);

    /**
     * Retrieves all price entries associated with a given product SKU.
     *
     * @param productSku The product SKU entity for which prices are to be fetched.
     * @return A list of PriceDto objects representing the prices for the specified product SKU.
     */
    List<PriceDto> getByProductSkuId(ProductSkuEntity productSku);

    /**
     * Retrieves the latest price for a specified product SKU by its ID.
     *
     * @param productSkuId The ID of the product SKU for which the latest price is being retrieved.
     * @return An Optional containing the latest PriceDto for the specified product SKU, or empty if not found.
     */
    Optional<PriceDto> getLatestPriceByProductSkuId(Integer productSkuId);
}
