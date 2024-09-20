package com.nmt.groceryfinder.common.enums;

import lombok.Getter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/29/2024
 */
@Getter
public enum BrandBusinessType {
    GROCERY_BUSINESS("GROCERY"),
    ELECTRONICS_BUSINESS("ELECTRONICS"),
    FASHION_BUSINESS("FASHION"),
    SERVICE_BUSINESS("SERVICE");

    private final String brandBusiness;
    BrandBusinessType(String brandBusiness){
        this.brandBusiness = brandBusiness;
    }
}
