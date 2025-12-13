package com.nexusartifex.api.dto.response;

import java.util.Map;
import java.util.UUID;

/**
 * Response DTO para nó.
 * 
 * @see components/schemas/NodeResponse no OpenAPI
 */
public record NodeResponse(
        UUID id,
        String label,
        String summary,
        Map<String, Object> visualData) {
}
