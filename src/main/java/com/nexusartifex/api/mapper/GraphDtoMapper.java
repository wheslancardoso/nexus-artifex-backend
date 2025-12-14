package com.nexusartifex.api.mapper;

import com.nexusartifex.api.dto.response.EdgeResponse;
import com.nexusartifex.api.dto.response.GraphResponse;
import com.nexusartifex.api.dto.response.NodeResponse;
import com.nexusartifex.domain.model.Edge;
import com.nexusartifex.domain.model.Graph;
import com.nexusartifex.domain.model.Node;

import java.util.List;

/**
 * Mapper para conversão entre Graph Domain e DTOs.
 */
public final class GraphDtoMapper {

    private GraphDtoMapper() {
        // Utility class
    }

    /**
     * Converte Graph Domain para GraphResponse DTO.
     */
    public static GraphResponse toResponse(Graph domain) {
        if (domain == null) {
            return null;
        }
        var nodeResponses = domain.getNodes().stream()
                .map(GraphDtoMapper::toNodeResponse)
                .toList();
        var edgeResponses = domain.getEdges().stream()
                .map(GraphDtoMapper::toEdgeResponse)
                .toList();
        return new GraphResponse(nodeResponses, edgeResponses);
    }

    private static NodeResponse toNodeResponse(Node node) {
        return new NodeResponse(
                node.getId(),
                node.getLabel(),
                node.getSummary(),
                node.getVisualData());
    }

    private static EdgeResponse toEdgeResponse(Edge edge) {
        return new EdgeResponse(
                edge.getSource(),
                edge.getTarget(),
                edge.getRelationship());
    }
}
