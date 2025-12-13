package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.request.EvolutionRequest;
import com.nexusartifex.api.dto.response.EvolutionResponse;
import com.nexusartifex.api.dto.response.NodeResponse;
import com.nexusartifex.domain.model.ScamperTechnique;
import com.nexusartifex.domain.services.EvolutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para evolução criativa usando técnica SCAMPER.
 */
@RestController
@RequestMapping("/api/v1/nodes")
public class EvolutionController {

    private final EvolutionService evolutionService;

    public EvolutionController(EvolutionService evolutionService) {
        this.evolutionService = evolutionService;
    }

    /**
     * Evoluir um nó usando SCAMPER.
     * POST /api/v1/nodes/{nodeId}/evolve
     */
    @PostMapping("/{nodeId}/evolve")
    public ResponseEntity<EvolutionResponse> evolveNode(
            @PathVariable UUID nodeId,
            @RequestBody EvolutionRequest request) {
        var technique = ScamperTechnique.valueOf(request.technique());
        var count = request.count() != null ? request.count() : 3;
        var evolution = evolutionService.evolve(nodeId, technique, count);

        var generatedNodes = evolution.getGeneratedNodes().stream()
                .map(n -> new NodeResponse(n.getId(), n.getLabel(), n.getSummary(), n.getVisualData()))
                .toList();

        var response = new EvolutionResponse(
                evolution.getOriginalNodeId(),
                evolution.getTechnique().name(),
                generatedNodes);
        return ResponseEntity.ok(response);
    }
}
