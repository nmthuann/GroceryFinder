package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.modules.products.domain.mappers.PriceMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.PriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreatePriceDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.PriceEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.PriceIdEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import com.nmt.groceryfinder.modules.products.repositories.IPriceRepository;
import com.nmt.groceryfinder.modules.products.services.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PriceService
        extends AbstractBaseService<PriceEntity, PriceIdEntity, PriceDto>
        implements IPriceService
{
    private final IPriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Autowired
    public PriceService(IPriceRepository priceRepository, PriceMapper priceMapper) {
        super(priceRepository, priceMapper);
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public Optional<PriceDto> createOne(ProductSkuEntity productSkuCreated, CreatePriceDto data) {
        PriceEntity newPrice = this.priceMapper.generatePrice(data, productSkuCreated);
        return Optional.ofNullable(this.priceMapper.toDto(this.priceRepository.save(newPrice)));
    }

    @Override
    public  List<PriceDto> getByProductSkuId(ProductSkuEntity productSku) {
        List<PriceEntity> priceEntities = this.priceRepository.findTop2ByProductSkuOrderByBeginAtDesc(productSku);
        return StreamSupport.stream(priceEntities.spliterator(), false)
                .map(entity -> this.priceMapper.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PriceDto> getLatestPriceByProductSkuId(Integer productSkuId) {
        Date currentDate = new Date();
        Optional<PriceEntity> findPrice = priceRepository.findLatestPriceByProductSkuId(productSkuId, currentDate);
        return findPrice.map(priceEntity -> this.priceMapper.toDto(priceEntity));
    }
}
