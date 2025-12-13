package com.nexusartifex.api.dto;

import java.util.UUID;

/**
 * Response DTO para aresta do grafo.
 */
public record EdgeResponse(
        UUID source,
        UUID target,
        String relationship) {
}
