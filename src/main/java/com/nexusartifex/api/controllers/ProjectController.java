package com.nexusartifex.api.controllers;

import com.nexusartifex.api.dto.request.CreateProjectRequest;
import com.nexusartifex.api.dto.response.ProjectResponse;
import com.nexusartifex.api.mapper.ProjectDtoMapper;
import com.nexusartifex.domain.services.ProjectService;
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

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Criar novo projeto.
     * POST /api/v1/projects
     */
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody CreateProjectRequest request) {
        var project = projectService.create(ProjectDtoMapper.extractName(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProjectDtoMapper.toResponse(project));
    }

    /**
     * Listar projetos.
     * GET /api/v1/projects
     */
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> listProjects() {
        var projects = projectService.findAll();
        var response = projects.stream()
                .map(ProjectDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
