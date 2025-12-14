package com.nexusartifex.infrastructure.persistence.mapper;

import com.nexusartifex.domain.model.Project;
import com.nexusartifex.infrastructure.persistence.entity.ProjectEntity;

/**
 * Mapper para conversão entre Project (domain) e ProjectEntity (persistence).
 */
public final class ProjectMapper {

    private ProjectMapper() {
        // Utility class
    }

    /**
     * Converte Domain para Entity.
     */
    public static ProjectEntity toEntity(Project domain) {
        if (domain == null) {
            return null;
        }
        return new ProjectEntity(
                domain.getId(),
                domain.getName(),
                domain.getCreatedAt());
    }

    /**
     * Converte Entity para Domain.
     */
    public static Project toDomain(ProjectEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Project(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt());
    }
}
