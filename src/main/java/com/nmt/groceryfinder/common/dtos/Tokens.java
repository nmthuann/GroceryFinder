package com.nmt.groceryfinder.common.dtos;

public record Tokens (
        String accessToken,
        String refreshToken
) {}