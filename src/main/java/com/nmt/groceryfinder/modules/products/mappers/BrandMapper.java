package com.nmt.groceryfinder.modules.products.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.dto.BrandDto;
import com.nmt.groceryfinder.modules.products.entities.BrandEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper extends AbstractModelMapper<BrandEntity, Integer, BrandDto> {

    @Autowired
    public BrandMapper(ModelMapper modelMapper) {
        super(modelMapper, BrandEntity.class, BrandDto.class);
    }

}