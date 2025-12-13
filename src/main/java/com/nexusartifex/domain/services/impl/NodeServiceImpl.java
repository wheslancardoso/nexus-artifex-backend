package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Node;
import com.nexusartifex.domain.model.NodeType;
import com.nexusartifex.domain.services.NodeService;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação placeholder do serviço de Nós.
 */
@Service
public class NodeServiceImpl implements NodeService {

    private final NodeRepository nodeRepository;

    public NodeServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Node create(UUID projectId, NodeType type, String label, String summary) {
        // Placeholder: retorna nó com dados mínimos
        return new Node(UUID.randomUUID(), projectId, type, label, summary, Collections.emptyMap());
    }

    @Override
    public Optional<Node> findById(UUID nodeId) {
        // Placeholder: retorna vazio
        return Optional.empty();
    }
}
