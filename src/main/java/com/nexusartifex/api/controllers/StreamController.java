package com.nexusartifex.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para streaming de eventos em tempo real (SSE).
 */
@RestController
@RequestMapping("/api/v1/projects")
public class StreamController {

    /**
     * Stream de novos nós em tempo real.
     * GET /api/v1/projects/{projectId}/stream
     */
    @GetMapping(value = "/{projectId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<?> streamProjectEvents(@PathVariable UUID projectId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
