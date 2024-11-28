package com.nmt.groceryfinder.common.messages;

import lombok.Getter;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/28/2024
 */
@Getter
public enum ProductMessages {
    SKU_CREATION_DETAIL_MESSAGE("Please verify the input data and ensure the product ID is valid.")
    ;
    private final String message;

    ProductMessages(String message){
        this.message = message;
    }
}
