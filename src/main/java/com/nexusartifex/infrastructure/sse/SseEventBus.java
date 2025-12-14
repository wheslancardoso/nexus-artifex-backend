package com.nexusartifex.infrastructure.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Event Bus para gerenciar conexões SSE por projeto.
 * Permite registrar e remover emitters de forma thread-safe.
 */
@Component
public class SseEventBus {

    /**
     * Mapa de projectId -> lista de emitters conectados.
     */
    private final ConcurrentHashMap<UUID, List<SseEmitter>> projectEmitters = new ConcurrentHashMap<>();

    /**
     * Registra um emitter para um projeto.
     *
     * @param projectId ID do projeto
     * @param emitter   SseEmitter a registrar
     */
    public void register(UUID projectId, SseEmitter emitter) {
        projectEmitters
                .computeIfAbsent(projectId, k -> new CopyOnWriteArrayList<>())
                .add(emitter);
    }

    /**
     * Remove um emitter de um projeto.
     *
     * @param projectId ID do projeto
     * @param emitter   SseEmitter a remover
     */
    public void unregister(UUID projectId, SseEmitter emitter) {
        var emitters = projectEmitters.get(projectId);
        if (emitters != null) {
            emitters.remove(emitter);
            // Limpar entrada se não houver mais emitters
            if (emitters.isEmpty()) {
                projectEmitters.remove(projectId);
            }
        }
    }

    /**
     * Retorna a lista de emitters para um projeto.
     *
     * @param projectId ID do projeto
     * @return Lista de emitters (pode ser vazia, nunca null)
     */
    public List<SseEmitter> getEmitters(UUID projectId) {
        return projectEmitters.getOrDefault(projectId, List.of());
    }

    /**
     * Retorna o número de conexões ativas para um projeto.
     *
     * @param projectId ID do projeto
     * @return Número de conexões
     */
    public int getConnectionCount(UUID projectId) {
        var emitters = projectEmitters.get(projectId);
        return emitters != null ? emitters.size() : 0;
    }

    /**
     * Retorna o número total de conexões ativas.
     *
     * @return Número total de conexões
     */
    public int getTotalConnectionCount() {
        return projectEmitters.values().stream()
                .mapToInt(List::size)
                .sum();
    }
}
