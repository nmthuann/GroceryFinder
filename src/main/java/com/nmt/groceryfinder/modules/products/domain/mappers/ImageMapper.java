package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ImageDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ImageEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImageMapper extends AbstractModelMapper<ImageEntity, UUID, ImageDto> {

    @Autowired
    public ImageMapper(ModelMapper modelMapper) {
        super(modelMapper, ImageEntity.class, ImageDto.class);
    }

    public List<ImageDto> mapEntitiesToDto(List<ImageEntity> imageEntities){
        List<ImageDto> imageDtos = new ArrayList<>();
        for(ImageEntity imageEntity: imageEntities){
           imageDtos.add(toDto(imageEntity));
        }
        return imageDtos;
    }
}
