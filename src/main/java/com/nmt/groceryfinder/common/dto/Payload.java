package com.nmt.groceryfinder.common.dto;

import java.util.UUID;

public record Payload(UUID userId, String email, String role) {
}
