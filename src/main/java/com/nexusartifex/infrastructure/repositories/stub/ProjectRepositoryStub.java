package com.nexusartifex.infrastructure.repositories.stub;

import com.nexusartifex.domain.model.Project;
import com.nexusartifex.infrastructure.repositories.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação stub do ProjectRepository (sem persistência real).
 */
@Repository
public class ProjectRepositoryStub implements ProjectRepository {

    @Override
    public Project save(Project project) {
        return project;
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Project> findAll() {
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
