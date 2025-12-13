package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Project;
import com.nexusartifex.domain.services.ProjectService;
import com.nexusartifex.infrastructure.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação placeholder do serviço de Projetos.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project create(String name) {
        // Placeholder: retorna projeto com dados mínimos
        return new Project(UUID.randomUUID(), name, OffsetDateTime.now());
    }

    @Override
    public List<Project> findAll() {
        // Placeholder: retorna lista vazia
        return Collections.emptyList();
    }

    @Override
    public Optional<Project> findById(UUID id) {
        // Placeholder: retorna vazio
        return Optional.empty();
    }
}
