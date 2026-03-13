package com.nmt.groceryfinder.modules.auth.responses;

public record AuthenticationResponseDto(
         Boolean success,
         String message
) {
}
