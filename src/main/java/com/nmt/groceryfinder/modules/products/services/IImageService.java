package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ImageDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateImageDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface IImageService extends IBaseService<UUID, ImageDto> {
    /**
     * Creates and associates a list of images for the specified product.
     *
     * @param productCreated The product entity to which the images will be associated.
     * @param data A list of CreateImageDto objects containing the details of each image to be created.
     * @return A list of ImageDto objects representing the created images.
     */
    List<ImageDto> createImages(ProductEntity productCreated, List<CreateImageDto> data);
}
