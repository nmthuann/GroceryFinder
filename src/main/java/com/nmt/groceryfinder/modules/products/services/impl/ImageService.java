package com.nmt.groceryfinder.modules.products.services.impl;


import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.modules.products.domain.mappers.ImageMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ImageDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateImageDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ImageEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.repositories.IImageRepository;
import com.nmt.groceryfinder.modules.products.services.IImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ImageService
        extends AbstractBaseService<ImageEntity, UUID, ImageDto>
        implements IImageService
{
    private final IImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Autowired
    public ImageService(IImageRepository imageRepository, ImageMapper imageMapper) {
        super(imageRepository, imageMapper);
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    @Transactional
    public List<ImageDto> createImages(ProductEntity productCreated, List<CreateImageDto> data) {
        List<ImageEntity> images = new ArrayList<>();
        for (CreateImageDto createImageDto : data) {
            ImageEntity newImage = this.imageMapper.generateImage(createImageDto, productCreated);
            images.add(imageRepository.save(newImage));
        }
        return  StreamSupport.stream(images.spliterator(), false)
                .map(entity -> imageMapper.toDto(entity))
                .collect(Collectors.toList());
    }
}
