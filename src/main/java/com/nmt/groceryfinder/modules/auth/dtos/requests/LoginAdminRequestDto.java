package com.nmt.groceryfinder.modules.auth.dtos.requests;

import jakarta.validation.constraints.NotBlank;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/5/2024
 */
public record LoginAdminRequestDto(
        @NotBlank(message = "email cannot be empty or blank")
        String email,
        @NotBlank(message = "password cannot be empty or blank")
        String password
) {
}
