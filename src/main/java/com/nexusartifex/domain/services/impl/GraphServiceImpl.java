package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Graph;
import com.nexusartifex.domain.services.GraphService;
import com.nexusartifex.infrastructure.repositories.GraphRepository;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

/**
 * Implementação placeholder do serviço de Grafo.
 */
@Service
public class GraphServiceImpl implements GraphService {

    private final NodeRepository nodeRepository;
    private final GraphRepository graphRepository;

    public GraphServiceImpl(NodeRepository nodeRepository, GraphRepository graphRepository) {
        this.nodeRepository = nodeRepository;
        this.graphRepository = graphRepository;
    }

    @Override
    public Graph getProjectGraph(UUID projectId) {
        // Placeholder: retorna grafo vazio
        return new Graph(Collections.emptyList(), Collections.emptyList());
    }
}
