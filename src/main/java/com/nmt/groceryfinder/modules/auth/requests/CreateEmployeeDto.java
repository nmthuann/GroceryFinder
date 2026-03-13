package com.nmt.groceryfinder.modules.auth.requests;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/5/2024
 */
public record CreateEmployeeDto(
        String cccd,
        Double salary,
        String position
) {
}
