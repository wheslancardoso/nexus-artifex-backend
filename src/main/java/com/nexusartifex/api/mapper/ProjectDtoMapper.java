package com.nexusartifex.api.mapper;

import com.nexusartifex.api.dto.request.CreateProjectRequest;
import com.nexusartifex.api.dto.response.ProjectResponse;
import com.nexusartifex.domain.model.Project;

/**
 * Mapper para conversão entre DTOs e Domain de Project.
 */
public final class ProjectDtoMapper {

    private ProjectDtoMapper() {
        // Utility class
    }

    /**
     * Converte Domain para Response DTO.
     */
    public static ProjectResponse toResponse(Project domain) {
        if (domain == null) {
            return null;
        }
        return new ProjectResponse(
                domain.getId(),
                domain.getName(),
                domain.getCreatedAt());
    }

    /**
     * Extrai nome do Request DTO.
     */
    public static String extractName(CreateProjectRequest request) {
        return request != null ? request.name() : null;
    }
}
