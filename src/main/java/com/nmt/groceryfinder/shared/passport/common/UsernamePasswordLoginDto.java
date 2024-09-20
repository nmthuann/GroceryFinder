package com.nmt.groceryfinder.shared.passport.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/14/2024
 */
public record UsernamePasswordLoginDto(
        @NotEmpty(message = "Email is required")
        @Email
        String email,
        @NotEmpty(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {
}
