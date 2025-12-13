package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.request.EvolutionRequest;
import com.nexusartifex.api.dto.response.EvolutionResponse;
import com.nexusartifex.domain.services.EvolutionService;
import org.springframework.http.HttpStatus;
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
        // TODO: mapear DTO -> domain, chamar service.evolve(), mapear domain -> DTO
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
