package com.nmt.groceryfinder.common.enums;

import lombok.Getter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/19/2024
 */
@Getter
public enum ProductStatusEnum {
    POST_ACTIVE("ACTIVE"),
    POST_UPDATED("UPDATED"),
    POST_STOPPED("STOPPED");
    private final String status;
    ProductStatusEnum(String status) {
        this.status = status;
    }
}
