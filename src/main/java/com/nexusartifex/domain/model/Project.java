package com.nexusartifex.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Model de domínio para Projeto criativo.
 */
public class Project {

    private UUID id;
    private String name;
    private OffsetDateTime createdAt;

    public Project() {
    }

    public Project(UUID id, String name, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
