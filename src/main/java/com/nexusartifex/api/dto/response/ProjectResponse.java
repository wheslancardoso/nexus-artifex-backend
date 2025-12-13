package com.nexusartifex.api.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Response DTO para projeto.
 * 
 * @see components/schemas/ProjectResponse no OpenAPI
 */
public record ProjectResponse(
        UUID id,
        String name,
        OffsetDateTime createdAt) {
}
