package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.CreateNodeRequest;
import com.nexusartifex.api.dto.NodeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gerenciamento de nós do grafo criativo.
 */
@RestController
@RequestMapping("/api/v1")
public class NodeController {

    /**
     * Criar nó inicial em um projeto.
     * POST /api/v1/projects/{projectId}/nodes
     */
    @PostMapping("/projects/{projectId}/nodes")
    public ResponseEntity<NodeResponse> createNode(
            @PathVariable UUID projectId,
            @RequestBody CreateNodeRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * Buscar nó por ID.
     * GET /api/v1/nodes/{nodeId}
     */
    @GetMapping("/nodes/{nodeId}")
    public ResponseEntity<NodeResponse> getNodeById(@PathVariable UUID nodeId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
