package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.response.GraphResponse;
import com.nexusartifex.domain.services.GraphService;
import org.springframework.http.HttpStatus;
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
        // TODO: chamar service.getProjectGraph(), mapear domain -> DTO
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
