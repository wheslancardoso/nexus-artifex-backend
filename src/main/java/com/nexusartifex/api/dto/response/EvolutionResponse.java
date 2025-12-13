package com.nexusartifex.api.dto.response;

import java.util.List;
import java.util.UUID;

/**
 * Response DTO para evolução de nó.
 * 
 * @see components/schemas/EvolutionResponse no OpenAPI
 */
public record EvolutionResponse(
        UUID originalNodeId,
        String technique,
        List<NodeResponse> generatedNodes) {
}
