package com.nexusartifex.infrastructure.persistence.jpa;

import com.nexusartifex.infrastructure.persistence.entity.EdgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository para EdgeEntity.
 */
public interface EdgeJpaRepository extends JpaRepository<EdgeEntity, UUID> {

    List<EdgeEntity> findByProjectId(UUID projectId);

    List<EdgeEntity> findBySourceNodeId(UUID sourceNodeId);

    List<EdgeEntity> findByTargetNodeId(UUID targetNodeId);

    void deleteByProjectId(UUID projectId);
}
