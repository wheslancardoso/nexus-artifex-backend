package com.nexusartifex.api.controllers;

import com.nexusartifex.infrastructure.sse.SseEventBus;
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

        try {
            // Enviar evento inicial "connected"
            emitter.send(SseEmitter.event()
                    .name("connected")
                    .data("{\"status\":\"connected\",\"projectId\":\"" + projectId + "\"}"));
        } catch (IOException e) {
            sseEventBus.unregister(projectId, emitter);
            emitter.completeWithError(e);
        }

        // Callbacks para limpeza e remoção do bus
        emitter.onCompletion(() -> {
            sseEventBus.unregister(projectId, emitter);
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
}
