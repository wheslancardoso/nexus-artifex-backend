package com.nexusartifex.api.controllers;

import com.nexusartifex.infrastructure.sse.SseEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

/**
 * Controller para streaming de eventos via SSE (Server-Sent Events).
 */
@RestController
@RequestMapping("/api/v1/projects")
public class StreamController {

    private static final Logger log = LoggerFactory.getLogger(StreamController.class);

    private final SseEventBus sseEventBus;

    public StreamController(SseEventBus sseEventBus) {
        this.sseEventBus = sseEventBus;
    }

    /**
     * Abre uma conexão SSE para o projeto especificado.
     * GET /api/v1/projects/{projectId}/stream
     */
    @GetMapping(value = "/{projectId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamProject(@PathVariable UUID projectId) {
        // Timeout infinito (0L = sem timeout)
        SseEmitter emitter = new SseEmitter(0L);

        // Registrar no Event Bus
        sseEventBus.register(projectId, emitter);
        log.info("SSE conneção aberta para projeto {} (total: {})",
                projectId, sseEventBus.getConnectionCount(projectId));

        try {
            // Enviar evento inicial "connected"
            emitter.send(SseEmitter.event()
                    .name("connected")
                    .data("{\"status\":\"connected\",\"projectId\":\"" + projectId + "\"}"));
        } catch (IOException e) {
            log.warn("SSE falha ao enviar 'connected' para projeto {}", projectId);
            sseEventBus.unregister(projectId, emitter);
            emitter.completeWithError(e);
        }

        // Callbacks para limpeza e remoção do bus
        emitter.onCompletion(() -> {
            sseEventBus.unregister(projectId, emitter);
            log.info("SSE conneção fechada para projeto {} (restantes: {})",
                    projectId, sseEventBus.getConnectionCount(projectId));
        });

        emitter.onTimeout(() -> {
            sseEventBus.unregister(projectId, emitter);
            emitter.complete();
        });

        emitter.onError((ex) -> {
            sseEventBus.unregister(projectId, emitter);
            emitter.completeWithError(ex);
        });

        return emitter;
    }

    /**
     * Endpoint temporário para teste de publicação SSE.
     * POST /api/v1/projects/{projectId}/events/test
     * 
     * Publica um evento de teste para todas as conexões SSE do projeto.
     */
    @PostMapping("/{projectId}/events/test")
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void publishTestEvent(@PathVariable UUID projectId) {
        sseEventBus.publish(projectId, "test",
                "{\"message\":\"Evento de teste\",\"timestamp\":\"" + java.time.Instant.now() + "\"}");
    }
}
