package com.nmt.groceryfinder.exceptions.messages;

import lombok.Getter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/26/2024
 */
@Getter
public enum InventoriesModuleExceptionMessages {
    WAREHOUSE_NOT_FOUND("WAREHOUSE NOTFOUND."),
    WAREHOUSE_NOT_DELETE("WAREHOUSE_NOT_DELETE is STORE fk reference."),
    STORE_NOT_FOUND("STORE NOTFOUND."),
    ;


    private final String message;
    InventoriesModuleExceptionMessages(String message){
        this.message = message;
    }
}
