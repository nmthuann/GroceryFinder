package com.nmt.groceryfinder.modules.auth.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto (
        @NotBlank(message = "firstParameter cannot be empty or blank")
        String firstParameter,
        @NotBlank(message = "secondParameter cannot be empty or blank")
        String secondParameter,
        @NotNull(message = "authMethodId may not be null")
        Integer authMethodId
) {
}
