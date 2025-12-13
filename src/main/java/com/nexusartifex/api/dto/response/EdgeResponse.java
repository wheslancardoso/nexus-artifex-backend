package com.nexusartifex.api.dto.response;

import java.util.UUID;

/**
 * Response DTO para aresta do grafo.
 * 
 * @see components/schemas/EdgeResponse no OpenAPI
 */
public record EdgeResponse(
        UUID source,
        UUID target,
        String relationship) {
}
