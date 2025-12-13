package com.nexusartifex.api.dto;

import java.util.List;

/**
 * Response DTO para grafo completo do projeto.
 */
public record GraphResponse(
        List<NodeResponse> nodes,
        List<EdgeResponse> edges) {
}
