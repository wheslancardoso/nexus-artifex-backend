package com.nexusartifex.api.dto;

import java.util.Map;
import java.util.UUID;

/**
 * Response DTO para nó.
 */
public record NodeResponse(
        UUID id,
        String label,
        String summary,
        Map<String, Object> visualData) {
}
