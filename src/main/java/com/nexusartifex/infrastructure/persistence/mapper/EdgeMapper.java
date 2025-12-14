package com.nexusartifex.infrastructure.persistence.mapper;

import com.nexusartifex.domain.model.Edge;
import com.nexusartifex.infrastructure.persistence.entity.EdgeEntity;

import java.util.UUID;

/**
 * Mapper para conversão entre Edge (domain) e EdgeEntity (persistence).
 */
public final class EdgeMapper {

    private EdgeMapper() {
        // Utility class
    }

    /**
     * Converte Domain para Entity.
     * 
     * @param domain    Edge de domínio
     * @param projectId ID do projeto (necessário para persistência)
     */
    public static EdgeEntity toEntity(Edge domain, UUID projectId) {
        if (domain == null) {
            return null;
        }
        return new EdgeEntity(
                null, // ID gerado pelo banco
                projectId,
                domain.getSource(),
                domain.getTarget(),
                domain.getRelationship());
    }

    /**
     * Converte Entity para Domain.
     */
    public static Edge toDomain(EdgeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Edge(
                entity.getSourceNodeId(),
                entity.getTargetNodeId(),
                entity.getRelationship());
    }
}
