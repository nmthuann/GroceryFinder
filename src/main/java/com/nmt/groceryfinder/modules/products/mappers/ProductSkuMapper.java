package com.nmt.groceryfinder.modules.products.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.products.dto.ProductSkuDto;
import com.nmt.groceryfinder.modules.products.requests.CreateProductSkuDto;
import com.nmt.groceryfinder.modules.products.entities.ProductSkuEntity;
import com.nmt.groceryfinder.utils.SlugUtil;
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
        productSkuEntity.setSlug(SlugUtil.createSlug(data.skuName()));
        productSkuEntity.setBarcode(data.barcode());
        productSkuEntity.setSkuName(data.skuName());
        productSkuEntity.setSkuAttributes(data.skuAttributes());
        productSkuEntity.setStatus(true);
        productSkuEntity.setImage(data.image());
        return productSkuEntity;
    }
}
