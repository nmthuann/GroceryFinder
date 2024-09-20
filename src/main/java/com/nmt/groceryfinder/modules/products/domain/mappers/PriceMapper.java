package com.nmt.groceryfinder.modules.products.domain.mappers;


import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.PriceEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.PriceIdEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PriceMapper extends AbstractModelMapper<PriceEntity, PriceIdEntity, PriceDto> {

    @Autowired
    public PriceMapper(ModelMapper modelMapper) {
        super(modelMapper, PriceEntity.class, PriceDto.class);
        this.modelMapper.typeMap(PriceEntity.class, PriceDto.class).addMapping(
                src -> src.getId().getBeginAt(), PriceDto::setBeginAt
        );
        this.modelMapper.typeMap(PriceDto.class, PriceEntity.class).addMapping(
                PriceDto::getBeginAt, (dest, v) -> dest.getId().setBeginAt((Date) v)
        );
    }
}