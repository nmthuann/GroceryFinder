package com.nmt.groceryfinder.modules.auth.dtos.responses;

public record LoginResponseDto(
        String email,
        String firstName,
        String lastName,
        String avatarUrl,
        String address,
        String accessToken,
        String refreshToken
) {
}
