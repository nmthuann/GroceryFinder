package com.nmt.groceryfinder.modules.products.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductSkuEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductSkuMapper  extends AbstractModelMapper<ProductSkuEntity, Integer, ProductSkuDto> {

    @Autowired
    public ProductSkuMapper(ModelMapper modelMapper) {
        super(modelMapper, ProductSkuEntity.class, ProductSkuDto.class);
    }

    public ProductSkuEntity generateProductSku(CreateProductSkuDto data){
        ProductSkuEntity productSkuEntity = new ProductSkuEntity();
        productSkuEntity.setSkuNo(data.skuNo());
        productSkuEntity.setBarcode(data.barcode());
        productSkuEntity.setSkuName(data.skuName());
        productSkuEntity.setSkuDescription(data.skuDescription());
        productSkuEntity.setStatus(true);
        return productSkuEntity;
    }
}
