package com.nmt.groceryfinder.modules.users.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record AccountDto (
        UUID id,
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 50, message = "Email should not be longer than 50 characters")
        String email,
        @NotNull(message = "Status is required")
        Boolean status,
        @NotBlank(message = "Sub is required")
        String sub,
        @NotBlank(message = "password is required")
        String password,
        @Size(max = 255, message = "Refresh token should not be longer than 255 characters")
        String refreshToken,
        String authMethod,
         String role
) {}