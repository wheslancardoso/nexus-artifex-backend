package com.nexusartifex.infrastructure.persistence.jpa;

import com.nexusartifex.infrastructure.persistence.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA Repository para ProjectEntity.
 */
public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, UUID> {
}
