package com.nmt.groceryfinder.modules.auth.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record RegisterRequestDto(
        @NotEmpty(message = "Email is required")
        @Email
        String email,
        @NotEmpty(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,
        @NotEmpty String phone,
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotEmpty String address,
        String gender,
        String avatarUrl,
        Date birthday,
        Integer authMethodId,
        Integer roleId
) {
}
