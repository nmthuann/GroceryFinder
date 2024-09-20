package com.nmt.groceryfinder.modules.auth.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/24/2024
 */
public record CheckOtpRequestDto(
        @NotEmpty(message = "Email is required")
        @Email
        String email,
        @NotEmpty(message = "OTP is required")
        @Size(max = 6, message = "Email should not be longer than 50 characters")
        String otp
) {
}
