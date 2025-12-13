package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.response.EdgeResponse;
import com.nexusartifex.api.dto.response.GraphResponse;
import com.nexusartifex.api.dto.response.NodeResponse;
import com.nexusartifex.domain.services.GraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para visualização do grafo de ideias.
 */
@RestController
@RequestMapping("/api/v1/projects")
public class GraphController {

    private final GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    /**
     * Obter grafo completo do projeto.
     * GET /api/v1/projects/{projectId}/graph
     */
    @GetMapping("/{projectId}/graph")
    public ResponseEntity<GraphResponse> getProjectGraph(@PathVariable UUID projectId) {
        var graph = graphService.getProjectGraph(projectId);

        var nodes = graph.getNodes().stream()
                .map(n -> new NodeResponse(n.getId(), n.getLabel(), n.getSummary(), n.getVisualData()))
                .toList();

        var edges = graph.getEdges().stream()
                .map(e -> new EdgeResponse(e.getSource(), e.getTarget(), e.getRelationship()))
                .toList();

        var response = new GraphResponse(nodes, edges);
        return ResponseEntity.ok(response);
    }
}
