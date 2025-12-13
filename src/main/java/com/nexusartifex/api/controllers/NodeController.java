package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.request.CreateNodeRequest;
import com.nexusartifex.api.dto.response.NodeResponse;
import com.nexusartifex.domain.services.NodeService;
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

    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    /**
     * Criar nó inicial em um projeto.
     * POST /api/v1/projects/{projectId}/nodes
     */
    @PostMapping("/projects/{projectId}/nodes")
    public ResponseEntity<NodeResponse> createNode(
            @PathVariable UUID projectId,
            @RequestBody CreateNodeRequest request) {
        // TODO: mapear DTO -> domain, chamar service, mapear domain -> DTO
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Buscar nó por ID.
     * GET /api/v1/nodes/{nodeId}
     */
    @GetMapping("/nodes/{nodeId}")
    public ResponseEntity<NodeResponse> getNodeById(@PathVariable UUID nodeId) {
        // TODO: chamar service.findById(), mapear domain -> DTO
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
