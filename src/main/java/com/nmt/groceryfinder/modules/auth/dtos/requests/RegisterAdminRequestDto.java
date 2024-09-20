package com.nmt.groceryfinder.modules.auth.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/5/2024
 */
public record RegisterAdminRequestDto(
        @NotEmpty(message = "Email is required")
        @Email
        String email,
        @NotEmpty String phone,
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotEmpty String address,
        String gender,
        String avatarURL,
        Date birthday,

        String cccd,
        Double salary,
        String position,
        Integer authMethodId,
        Integer roleId
) {
}
