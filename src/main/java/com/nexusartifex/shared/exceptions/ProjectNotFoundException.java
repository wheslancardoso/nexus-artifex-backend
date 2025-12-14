package com.nexusartifex.shared.exceptions;

import java.util.UUID;

/**
 * Exceção lançada quando um Project não é encontrado.
 */
public class ProjectNotFoundException extends RuntimeException {

    private final UUID projectId;

    public ProjectNotFoundException(UUID projectId) {
        super("Projeto não encontrado: " + projectId);
        this.projectId = projectId;
    }

    public ProjectNotFoundException(String message) {
        super(message);
        this.projectId = null;
    }

    public UUID getProjectId() {
        return projectId;
    }
}
