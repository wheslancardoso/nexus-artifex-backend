package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.CreateProjectRequest;
import com.nexusartifex.api.dto.ProjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciamento de projetos criativos.
 */
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    /**
     * Criar novo projeto.
     * POST /api/v1/projects
     */
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody CreateProjectRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * Listar projetos.
     * GET /api/v1/projects
     */
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> listProjects() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
