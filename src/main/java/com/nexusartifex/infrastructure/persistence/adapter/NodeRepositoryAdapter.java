package com.nexusartifex.infrastructure.persistence.adapter;

import com.nexusartifex.domain.model.Node;
import com.nexusartifex.infrastructure.persistence.jpa.NodeJpaRepository;
import com.nexusartifex.infrastructure.persistence.mapper.NodeMapper;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter que implementa NodeRepository usando JPA.
 */
@Repository
@Primary
public class NodeRepositoryAdapter implements NodeRepository {

    private final NodeJpaRepository jpaRepository;

    public NodeRepositoryAdapter(NodeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Node save(Node node) {
        var entity = NodeMapper.toEntity(node);
        var saved = jpaRepository.save(entity);
        return NodeMapper.toDomain(saved);
    }

    @Override
    public List<Node> saveAll(List<Node> nodes) {
        var entities = nodes.stream()
                .map(NodeMapper::toEntity)
                .toList();
        var saved = jpaRepository.saveAll(entities);
        return saved.stream()
                .map(NodeMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Node> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(NodeMapper::toDomain);
    }

    @Override
    public List<Node> findByProjectId(UUID projectId) {
        return jpaRepository.findByProjectId(projectId).stream()
                .map(NodeMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
