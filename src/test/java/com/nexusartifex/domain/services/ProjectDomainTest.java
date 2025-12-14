package com.nexusartifex.domain.services;

import com.nexusartifex.domain.model.Project;
import com.nexusartifex.shared.exceptions.InvalidProjectException;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para regras de domínio de Project.
 */
class ProjectDomainTest {

    @Test
    void project_shouldBeCreatedWithValidName() {
        Project project = new Project(UUID.randomUUID(), "Meu Projeto", OffsetDateTime.now());
        assertEquals("Meu Projeto", project.getName());
        assertNotNull(project.getId());
    }

    @Test
    void invalidProject_invalidName_shouldThrow() {
        InvalidProjectException ex = InvalidProjectException.invalidName();
        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().toLowerCase().contains("nome") ||
                ex.getMessage().toLowerCase().contains("obrigatório"));
    }

    @Test
    void invalidProject_nameTooLong_shouldThrow() {
        InvalidProjectException ex = InvalidProjectException.nameTooLong(255);
        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains("255"));
    }
}
