package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.EvolutionRequest;
import com.nexusartifex.api.dto.EvolutionResponse;
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

    /**
     * Evoluir um nó usando SCAMPER.
     * POST /api/v1/nodes/{nodeId}/evolve
     */
    @PostMapping("/{nodeId}/evolve")
    public ResponseEntity<EvolutionResponse> evolveNode(
            @PathVariable UUID nodeId,
            @RequestBody EvolutionRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
