package com.nmt.groceryfinder.shared.passport.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/14/2024
 */
public record OauthGoogleLoginDto(
        @NotEmpty(message = "Email is required")
        @Email
        String email,
        @NotEmpty(message = "sub is required")
        String sub
) {
}
