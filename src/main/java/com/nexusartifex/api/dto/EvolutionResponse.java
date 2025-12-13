package com.nexusartifex.api.dto;

import java.util.List;
import java.util.UUID;

/**
 * Response DTO para evolução de nó.
 */
public record EvolutionResponse(
        UUID originalNodeId,
        String technique,
        List<NodeResponse> generatedNodes) {
}
