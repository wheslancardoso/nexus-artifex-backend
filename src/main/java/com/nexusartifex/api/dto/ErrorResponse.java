package com.nexusartifex.api.dto;

import java.time.OffsetDateTime;

/**
 * Response DTO para erros da API.
 */
public record ErrorResponse(
        OffsetDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path) {
}
