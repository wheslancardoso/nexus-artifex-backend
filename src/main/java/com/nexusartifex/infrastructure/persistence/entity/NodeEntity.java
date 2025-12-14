package com.nexusartifex.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * Entity JPA para persistência de Nós.
 */
@Entity
@Table(name = "nodes")
public class NodeEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "label", nullable = false, length = 255)
    private String label;

    @Column(name = "summary", length = 1000)
    private String summary;

    @Column(name = "visual_data", columnDefinition = "jsonb")
    private String visualData;

    public NodeEntity() {
    }

    public NodeEntity(UUID id, UUID projectId, String type, String label, String summary, String visualData) {
        this.id = id;
        this.projectId = projectId;
        this.type = type;
        this.label = label;
        this.summary = summary;
        this.visualData = visualData;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getVisualData() {
        return visualData;
    }

    public void setVisualData(String visualData) {
        this.visualData = visualData;
    }
}
