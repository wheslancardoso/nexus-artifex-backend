package com.nexusartifex.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * Entity JPA para persistência de Arestas do Grafo.
 */
@Entity
@Table(name = "edges")
public class EdgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @Column(name = "source_node_id", nullable = false)
    private UUID sourceNodeId;

    @Column(name = "target_node_id", nullable = false)
    private UUID targetNodeId;

    @Column(name = "relationship", length = 100)
    private String relationship;

    public EdgeEntity() {
    }

    public EdgeEntity(UUID id, UUID projectId, UUID sourceNodeId, UUID targetNodeId, String relationship) {
        this.id = id;
        this.projectId = projectId;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
        this.relationship = relationship;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(UUID sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public UUID getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(UUID targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
