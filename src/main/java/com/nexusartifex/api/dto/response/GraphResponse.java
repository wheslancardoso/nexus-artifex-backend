package com.nexusartifex.api.dto.response;

import java.util.List;

/**
 * Response DTO para grafo completo do projeto.
 * 
 * @see components/schemas/GraphResponse no OpenAPI
 */
public record GraphResponse(
        List<NodeResponse> nodes,
        List<EdgeResponse> edges) {
}
