package com.nmt.groceryfinder.common.dtos;

import java.util.UUID;

public record Payload(UUID userId, String email, String role) {
}
