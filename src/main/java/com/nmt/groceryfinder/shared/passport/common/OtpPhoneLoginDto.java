package com.nmt.groceryfinder.shared.passport.common;

import jakarta.validation.constraints.NotEmpty;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/14/2024
 */
public record OtpPhoneLoginDto(
        @NotEmpty(message = "phone is required")
        String phone,
        @NotEmpty(message = "uid is required")
        String uid
) {
}
