package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.modules.products.domain.mappers.BrandMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.BrandDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.BrandEntity;
import com.nmt.groceryfinder.modules.products.repositories.IBrandRepository;
import com.nmt.groceryfinder.modules.products.services.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BrandService
        extends AbstractBaseService<BrandEntity, Integer, BrandDto>
        implements IBrandService
{
    private final IBrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Autowired
    public BrandService(IBrandRepository brandRepository, BrandMapper brandMapper) {
        super(brandRepository, brandMapper);
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public Iterable<BrandDto> getBrandsByBrandBusiness(String brandBusiness) {
        return brandRepository.findByBrandBusiness(brandBusiness).stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

}
