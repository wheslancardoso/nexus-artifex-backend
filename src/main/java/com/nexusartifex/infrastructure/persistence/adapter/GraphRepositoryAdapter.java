package com.nexusartifex.infrastructure.persistence.adapter;

import com.nexusartifex.domain.model.Edge;
import com.nexusartifex.infrastructure.persistence.entity.EdgeEntity;
import com.nexusartifex.infrastructure.persistence.jpa.EdgeJpaRepository;
import com.nexusartifex.infrastructure.persistence.mapper.EdgeMapper;
import com.nexusartifex.infrastructure.repositories.GraphRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Adapter que implementa GraphRepository usando JPA.
 */
@Repository
@Primary
public class GraphRepositoryAdapter implements GraphRepository {

    private final EdgeJpaRepository jpaRepository;

    public GraphRepositoryAdapter(EdgeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Edge save(Edge edge) {
        // Nota: projectId precisa ser fornecido via contexto
        // Por enquanto, usamos null e esperamos que seja definido antes
        var entity = new EdgeEntity(
                null,
                null, // projectId será definido pelo service
                edge.getSource(),
                edge.getTarget(),
                edge.getRelationship());
        var saved = jpaRepository.save(entity);
        return EdgeMapper.toDomain(saved);
    }

    @Override
    public List<Edge> saveAll(List<Edge> edges) {
        // Simplificado: sem projectId por ora
        var entities = edges.stream()
                .map(e -> new EdgeEntity(null, null, e.getSource(), e.getTarget(), e.getRelationship()))
                .toList();
        var saved = jpaRepository.saveAll(entities);
        return saved.stream()
                .map(EdgeMapper::toDomain)
                .toList();
    }

    @Override
    public List<Edge> findByProjectId(UUID projectId) {
        return jpaRepository.findByProjectId(projectId).stream()
                .map(EdgeMapper::toDomain)
                .toList();
    }

    @Override
    public List<Edge> findBySourceNodeId(UUID nodeId) {
        return jpaRepository.findBySourceNodeId(nodeId).stream()
                .map(EdgeMapper::toDomain)
                .toList();
    }

    @Override
    public List<Edge> findByTargetNodeId(UUID nodeId) {
        return jpaRepository.findByTargetNodeId(nodeId).stream()
                .map(EdgeMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteByProjectId(UUID projectId) {
        jpaRepository.deleteByProjectId(projectId);
    }
}
