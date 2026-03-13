package com.nmt.groceryfinder.modules.products.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.modules.products.dto.BrandDto;

public interface IBrandService extends IBaseService<Integer, BrandDto> {
    Iterable<BrandDto> getBrandsByBrandBusiness(String brandBusiness);
}
