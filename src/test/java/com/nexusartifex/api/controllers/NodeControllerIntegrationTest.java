package com.nexusartifex.api.controllers;

import com.nexusartifex.domain.model.Project;
import com.nexusartifex.infrastructure.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para NodeController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NodeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    private UUID projectId;

    @BeforeEach
    void setUp() {
        // Criar projeto para testes
        Project project = new Project(UUID.randomUUID(), "Projeto para Nodes", OffsetDateTime.now());
        Project saved = projectRepository.save(project);
        projectId = saved.getId();
    }

    @Test
    void createNode_shouldReturn201() throws Exception {
        String json = "{\"type\":\"CONCEPT\",\"label\":\"Ideia Teste\",\"summary\":\"Uma ideia\"}";

        mockMvc.perform(post("/api/v1/projects/" + projectId + "/nodes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.label").value("Ideia Teste"));
    }

    @Test
    void createNode_withBlankLabel_shouldReturn400() throws Exception {
        String json = "{\"type\":\"CONCEPT\",\"label\":\"\",\"summary\":\"sem label\"}";

        mockMvc.perform(post("/api/v1/projects/" + projectId + "/nodes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNode_notFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/v1/nodes/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound());
    }
}
