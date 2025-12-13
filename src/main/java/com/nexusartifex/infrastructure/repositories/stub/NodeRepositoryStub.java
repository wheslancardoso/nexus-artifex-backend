package com.nexusartifex.infrastructure.repositories.stub;

import com.nexusartifex.domain.model.Node;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação stub do NodeRepository (sem persistência real).
 */
@Repository
public class NodeRepositoryStub implements NodeRepository {

    @Override
    public Node save(Node node) {
        return node;
    }

    @Override
    public List<Node> saveAll(List<Node> nodes) {
        return nodes;
    }

    @Override
    public Optional<Node> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Node> findByProjectId(UUID projectId) {
        return Collections.emptyList();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public void deleteById(UUID id) {
        // stub
    }
}
