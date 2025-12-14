package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Project;
import com.nexusartifex.domain.services.ProjectService;
import com.nexusartifex.infrastructure.repositories.ProjectRepository;
import com.nexusartifex.shared.exceptions.InvalidProjectException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do serviço de Projetos com regras de negócio.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private static final int MAX_NAME_LENGTH = 255;

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project create(String name) {
        // Regra 1: Nome obrigatório
        if (name == null || name.isBlank()) {
            throw InvalidProjectException.invalidName();
        }

        // Regra 2: Nome não pode exceder limite
        String trimmedName = name.trim();
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            throw InvalidProjectException.nameTooLong(MAX_NAME_LENGTH);
        }

        // Regra 3: Geração de UUID
        UUID id = UUID.randomUUID();

        // Regra 4: Definição de createdAt
        OffsetDateTime createdAt = OffsetDateTime.now();

        var project = new Project(id, trimmedName, createdAt);
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
