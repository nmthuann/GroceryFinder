package com.nmt.groceryfinder.modules.auth.dtos.responses;

public record AuthenticationResponseDto(
         Boolean success,
         String message
) {
}
