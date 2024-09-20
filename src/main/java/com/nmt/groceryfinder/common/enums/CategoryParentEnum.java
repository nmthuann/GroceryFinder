package com.nmt.groceryfinder.common.enums;

import lombok.Getter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/7/2024
 */
@Getter
public enum CategoryParentEnum {
    ROOT_PARENT_ID(1),
    GROCERY_PARENT_ID(2),
    ELECTRONICS_PARENT_ID(3);
    private final int categoryId;
    CategoryParentEnum(int categoryId){
        this.categoryId = categoryId;
    }
}
