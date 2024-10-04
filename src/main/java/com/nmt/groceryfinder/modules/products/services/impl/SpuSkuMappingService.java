package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.modules.products.domain.mappers.SpuSkuMappingMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.SpuSkuMappingDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.SpuSkuMappingEntity;
import com.nmt.groceryfinder.modules.products.repositories.ISpuSkuMappingRepository;
import com.nmt.groceryfinder.modules.products.services.ISpuSkuMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SpuSkuMappingService
        extends AbstractBaseService<SpuSkuMappingEntity, Integer, SpuSkuMappingDto>
        implements ISpuSkuMappingService
{
    private final ISpuSkuMappingRepository spuSkuMappingRepository;
    private final SpuSkuMappingMapper spuSkuMappingMapper;


    @Autowired
    public SpuSkuMappingService(
            ISpuSkuMappingRepository spuSkuMappingRepository,
            SpuSkuMappingMapper spuSkuMappingMapper
    ) {
        super(spuSkuMappingRepository, spuSkuMappingMapper);
        this.spuSkuMappingRepository = spuSkuMappingRepository;
        this.spuSkuMappingMapper = spuSkuMappingMapper;

    }

    @Override
    public Optional<SpuSkuMappingDto> createOne(ProductEntity productCreated, ProductSkuEntity productSkuCreated) {
        SpuSkuMappingEntity spuSkuMappingEntity = new SpuSkuMappingEntity();
        spuSkuMappingEntity.setProduct(productCreated);
        spuSkuMappingEntity.setProductSku(productSkuCreated);
        return Optional.ofNullable(
                this.spuSkuMappingMapper.toDto(
                        this.spuSkuMappingRepository.save(spuSkuMappingEntity)
                )
        );
    }

    @Override
    public List<SpuSkuMappingDto> getSkusByProductId(UUID id) {
        return this.spuSkuMappingRepository.findAllByProductId(id).stream()
                .map(this.spuSkuMappingMapper::toDto)
                .collect(Collectors.toList());
    }
}
