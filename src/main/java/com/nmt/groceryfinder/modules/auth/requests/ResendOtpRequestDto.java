package com.nmt.groceryfinder.modules.auth.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/18/2024
 */
public record ResendOtpRequestDto(
        @NotEmpty(message = "Email is required")
        @Email
        String email
) {
}
