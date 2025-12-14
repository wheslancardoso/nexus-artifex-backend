package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Project;
import com.nexusartifex.domain.services.ProjectService;
import com.nexusartifex.infrastructure.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do serviço de Projetos usando repository real.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project create(String name) {
        var project = new Project(UUID.randomUUID(), name, OffsetDateTime.now());
        return projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return projectRepository.findById(id);
    }
}
