package com.nmt.groceryfinder.modules.auth.dtos.responses;

public record RegisterResponseDto(
        String email,
        String firstName,
        String lastName,
        String avatarUrl,
        String address,
        String accessToken,
        String refreshToken
) {
}
