package com.nexusartifex.api.mapper;

import com.nexusartifex.api.dto.request.CreateNodeRequest;
import com.nexusartifex.api.dto.response.NodeResponse;
import com.nexusartifex.domain.model.Node;
import com.nexusartifex.domain.model.NodeType;

/**
 * Mapper para conversão entre DTOs e Domain de Node.
 */
public final class NodeDtoMapper {

    private NodeDtoMapper() {
        // Utility class
    }

    /**
     * Converte Domain para Response DTO.
     */
    public static NodeResponse toResponse(Node domain) {
        if (domain == null) {
            return null;
        }
        return new NodeResponse(
                domain.getId(),
                domain.getLabel(),
                domain.getSummary(),
                domain.getVisualData());
    }

    /**
     * Extrai NodeType do Request DTO.
     */
    public static NodeType extractType(CreateNodeRequest request) {
        if (request == null || request.type() == null) {
            return null;
        }
        return NodeType.valueOf(request.type());
    }

    /**
     * Extrai label do Request DTO.
     */
    public static String extractLabel(CreateNodeRequest request) {
        return request != null ? request.label() : null;
    }

    /**
     * Extrai summary do Request DTO.
     */
    public static String extractSummary(CreateNodeRequest request) {
        return request != null ? request.summary() : null;
    }
}
