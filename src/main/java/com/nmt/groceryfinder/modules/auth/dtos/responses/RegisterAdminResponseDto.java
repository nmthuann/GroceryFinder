package com.nmt.groceryfinder.modules.auth.dtos.responses;


/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/6/2024
 */
public record RegisterAdminResponseDto(
        String email,
        String firstName,
        String lastName,
        String avatarUrl,
        String address,
        String accessToken,
        String refreshToken,
        String position
) {
}
