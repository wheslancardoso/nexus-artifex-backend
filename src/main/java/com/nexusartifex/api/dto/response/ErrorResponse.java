package com.nexusartifex.api.dto.response;

import java.time.OffsetDateTime;

/**
 * Response DTO para erros da API.
 * 
 * @see components/schemas/ErrorResponse no OpenAPI
 */
public record ErrorResponse(
        OffsetDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path) {
}
