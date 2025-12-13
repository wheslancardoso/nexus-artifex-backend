package com.nexusartifex.infrastructure.repositories.stub;

import com.nexusartifex.domain.model.Edge;
import com.nexusartifex.infrastructure.repositories.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Implementação stub do GraphRepository (sem persistência real).
 */
@Repository
public class GraphRepositoryStub implements GraphRepository {

    @Override
    public Edge save(Edge edge) {
        return edge;
    }

    @Override
    public List<Edge> saveAll(List<Edge> edges) {
        return edges;
    }

    @Override
    public List<Edge> findByProjectId(UUID projectId) {
        return Collections.emptyList();
    }

    @Override
    public List<Edge> findBySourceNodeId(UUID nodeId) {
        return Collections.emptyList();
    }

    @Override
    public List<Edge> findByTargetNodeId(UUID nodeId) {
        return Collections.emptyList();
    }

    @Override
    public void deleteByProjectId(UUID projectId) {
        // stub
    }
}
