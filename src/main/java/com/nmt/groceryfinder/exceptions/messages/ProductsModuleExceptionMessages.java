package com.nmt.groceryfinder.exceptions.messages;

import lombok.Getter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/20/2024
 */
@Getter
public enum ProductsModuleExceptionMessages {
    GET_PRODUCT_NOT_FOUND("GET_PRODUCT_NOT_FOUND."),
    GET_CATEGORY_NOT_FOUND("CATEGORY_PARENT_NOT_FOUND."),
    GET_BRAND_NOT_FOUND("GET_BRAND_NOT_FOUND."),
    GET_SUPPLIER_NOT_FOUND("GET_SUPPLIER_NOT_FOUND."),
    GET_DISCOUNT_NOT_FOUND("GET_BRAND_NOT_FOUND."),
    INSERT_IMAGES_FAIL("INSERT_IMAGES_FAIL."),
    CATEGORY_PARENT_NOT_FOUND("CATEGORY_PARENT_NOT_FOUND."),
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND."),
    CATEGORY_NOT_DELETE("CATEGORY_NOT_DELETE is product fk reference."),
    GET_PRODUCT_SKU_NOT_FOUND("GET_PRODUCT_SKU_NOT_FOUND."),
    SKU_CREATION_FAILED("SKU Creation Failed."),
    SKU_CREATION_FAILED_DETAIL("Unable to create SKU for the given product."),
    SKU_CREATION_BAD_REQUEST("Bad Request");
    ;




    private final String message;

    ProductsModuleExceptionMessages(String message){
        this.message = message;
    }
}
