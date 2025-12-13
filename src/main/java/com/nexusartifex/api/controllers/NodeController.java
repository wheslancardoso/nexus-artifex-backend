package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.request.CreateNodeRequest;
import com.nexusartifex.api.dto.response.NodeResponse;
import com.nexusartifex.domain.model.NodeType;
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
        var nodeType = NodeType.valueOf(request.type());
        var node = nodeService.create(projectId, nodeType, request.label(), request.summary());
        var response = new NodeResponse(node.getId(), node.getLabel(), node.getSummary(), node.getVisualData());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Buscar nó por ID.
     * GET /api/v1/nodes/{nodeId}
     */
    @GetMapping("/nodes/{nodeId}")
    public ResponseEntity<NodeResponse> getNodeById(@PathVariable UUID nodeId) {
        return nodeService.findById(nodeId)
                .map(node -> new NodeResponse(node.getId(), node.getLabel(), node.getSummary(), node.getVisualData()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
