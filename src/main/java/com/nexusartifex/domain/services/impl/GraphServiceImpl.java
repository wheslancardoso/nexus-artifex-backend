package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Graph;
import com.nexusartifex.domain.services.GraphService;
import com.nexusartifex.infrastructure.repositories.GraphRepository;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de Grafo usando repositories reais.
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
        var nodes = nodeRepository.findByProjectId(projectId);
        var edges = graphRepository.findByProjectId(projectId);
        return new Graph(nodes, edges);
    }
}
