package com.nmt.groceryfinder.modules.auth.dtos.requests;

import jakarta.validation.constraints.*;

import java.util.Date;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/16/2024
 */
public record CreateUserDto(
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 50, message = "Email should not be longer than 50 characters")
        String email,
        @NotBlank(message = "password is required")
        String hashedPassword,
        String refreshToken,
        String avatarUrl,
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        String gender,
        Date birthday,
        @NotEmpty String phone,
        @NotEmpty String address,
        @NotNull Integer authMethod,
        @NotNull Integer role
) {
}
