package com.nexusartifex.infrastructure.persistence.jpa;

import com.nexusartifex.infrastructure.persistence.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository para NodeEntity.
 */
public interface NodeJpaRepository extends JpaRepository<NodeEntity, UUID> {

    List<NodeEntity> findByProjectId(UUID projectId);
}
