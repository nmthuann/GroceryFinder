package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ReviewEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper  extends AbstractModelMapper<ReviewEntity, Integer, ReviewDto> {

    @Autowired
    public ReviewMapper(ModelMapper modelMapper) {
        super(modelMapper, ReviewEntity.class, ReviewDto.class);
    }
}
