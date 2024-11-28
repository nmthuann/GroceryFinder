package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
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
    public Optional<PriceDto> createOne(ProductSkuEntity productSkuCreated, CreatePriceDto data) throws ModuleException {
        PriceIdEntity id = new PriceIdEntity();
        id.setBeginAt(data.beginAt());
        id.setProductSku(productSkuCreated);
        Optional<PriceEntity> findById = this.priceRepository.findById(id);
        if(findById.isPresent()) {
            throw new ModuleException("Price entry already exists for the given SKU and start date");
        }
        PriceEntity newPrice = this.priceMapper.generatePrice(data, productSkuCreated);
        return Optional.ofNullable(this.priceMapper.toDto(this.priceRepository.save(newPrice)));
    }

    @Override
    public  List<PriceDto> getTop2ByProductSku(ProductSkuEntity productSku) {
        List<PriceEntity> priceEntities = this.priceRepository.findTop2ByProductSkuOrderByBeginAtDesc(productSku);
        return priceEntities.stream()
                .map(this.priceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceDto> getPricesByProductSkuId(Integer productSkuId) {
        List<PriceEntity> priceEntities = this.priceRepository.findAllByIdProductSkuId(productSkuId);
        return priceEntities.stream()
                .map(this.priceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PriceDto> getLatestPriceByProductSkuId(Integer productSkuId) {
        Date currentDate = new Date();
        Optional<PriceEntity> findPrice = priceRepository.findLatestPriceByProductSkuId(productSkuId, currentDate);
        return findPrice.map(this.priceMapper::toDto);
    }
}
