package com.nmt.groceryfinder.common.dto;

public record Tokens (
        String accessToken,
        String refreshToken
) {}