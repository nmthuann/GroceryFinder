package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ImageDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateImageDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface IImageService extends IBaseService<UUID, ImageDto> {
    List<ImageDto> createImages(ProductEntity productCreated, List<CreateImageDto> data);
}
