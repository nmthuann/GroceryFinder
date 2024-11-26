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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

        // Step 1: Fetch existing images for the product
        List<ImageEntity> existingImages = imageRepository.findAllByProductId(productCreated.getId());

        // Step 2: Create a set of new image URLs for easier comparison
        Set<String> newImageUrls = data.stream()
                .map(CreateImageDto::imageUrl)
                .collect(Collectors.toSet());

        // Step 3: Determine images to delete
        List<ImageEntity> imagesToDelete = new ArrayList<>();
        for (ImageEntity existingImage : existingImages) {
            if (!newImageUrls.contains(existingImage.getImageUrl())) {
                imagesToDelete.add(existingImage);
            }
        }

        // Step 4: Delete images that are no longer present in the new data
        imageRepository.deleteAll(imagesToDelete);

        // Step 5: Determine images to insert
        List<ImageEntity> imagesToInsert = new ArrayList<>();
        for (CreateImageDto createImageDto : data) {
            boolean existsInDb = existingImages.stream()
                    .anyMatch(existingImage -> existingImage.getImageUrl().equals(createImageDto.imageUrl()));

            if (!existsInDb) {
                ImageEntity newImage = imageMapper.generateImage(createImageDto, productCreated);
                imagesToInsert.add(imageRepository.save(newImage));
            }
        }

        // Step 6: Convert newly inserted images to DTOs and return
        return imagesToInsert.stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());

    }
}