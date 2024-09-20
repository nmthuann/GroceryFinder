package com.nmt.groceryfinder.common.enums;

import lombok.Getter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/7/2024
 */
@Getter
public enum WarehouseSellOrderEnum {
    WAREHOUSE_FIRST_BRANCH(1),
    WAREHOUSE_ONLINE(2)
    ;

    private final Integer warehouseId;
    WarehouseSellOrderEnum(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }
}
