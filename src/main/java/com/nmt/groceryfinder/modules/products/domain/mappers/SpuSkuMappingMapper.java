package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.SpuSkuMappingDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.SpuSkuMappingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpuSkuMappingMapper extends AbstractModelMapper<SpuSkuMappingEntity, Integer, SpuSkuMappingDto> {

    @Autowired
    public SpuSkuMappingMapper(ModelMapper modelMapper) {
        super(modelMapper, SpuSkuMappingEntity.class, SpuSkuMappingDto.class);
    }
}
