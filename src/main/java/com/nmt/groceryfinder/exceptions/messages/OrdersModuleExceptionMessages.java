package com.nmt.groceryfinder.exceptions.messages;


import lombok.Getter;

@Getter
public enum OrdersModuleExceptionMessages {
    ORDER_TRANSACTION_NOT_CREATED("ORDER_TRANSACTION_NOT_CREATED, so Order cancelled ."),
    PAYMENT_METHOD_NOT_FOUND("PAYMENT_METHOD_NOT_FOUND."),
    SHIPPING_METHOD_NOT_FOUND("SHIPPING_METHOD_NOT_FOUND."),
    USER_NOT_FOUND("USER_NOT_FOUND."),
    PRODUCT_SKU_NOT_FOUND("PRODUCT_SKU_NOT_FOUND."),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND."),
    PRODUCT_SKU_NOT_ENOUGH_QUANTITY("PRODUCT_SKU_NOT_ENOUGH_QUANTITY."),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND."),
    ;


    private final String message;
    OrdersModuleExceptionMessages(String message){
        this.message = message;
    }
}
