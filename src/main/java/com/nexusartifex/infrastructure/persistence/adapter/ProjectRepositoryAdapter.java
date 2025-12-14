package com.nexusartifex.infrastructure.persistence.adapter;

import com.nexusartifex.domain.model.Project;
import com.nexusartifex.infrastructure.persistence.jpa.ProjectJpaRepository;
import com.nexusartifex.infrastructure.persistence.mapper.ProjectMapper;
import com.nexusartifex.infrastructure.repositories.ProjectRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter que implementa ProjectRepository usando JPA.
 */
@Repository
@Primary
public class ProjectRepositoryAdapter implements ProjectRepository {

    private final ProjectJpaRepository jpaRepository;

    public ProjectRepositoryAdapter(ProjectJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Project save(Project project) {
        var entity = ProjectMapper.toEntity(project);
        var saved = jpaRepository.save(entity);
        return ProjectMapper.toDomain(saved);
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(ProjectMapper::toDomain);
    }

    @Override
    public List<Project> findAll() {
        return jpaRepository.findAll().stream()
                .map(ProjectMapper::toDomain)
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
