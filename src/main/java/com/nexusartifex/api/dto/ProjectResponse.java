package com.nexusartifex.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Response DTO para projeto.
 */
public record ProjectResponse(
        UUID id,
        String name,
        OffsetDateTime createdAt) {
}
