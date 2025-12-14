package com.nexusartifex.api.controllers;

import com.nexusartifex.ai.service.AiEvolutionService;
import com.nexusartifex.infrastructure.sse.SseEventBus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controller de healthcheck avançado com status de componentes.
 */
@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {

    private final DataSource dataSource;
    private final SseEventBus sseEventBus;
    private final AiEvolutionService aiEvolutionService;

    @Value("${features.ai.enabled:false}")
    private boolean aiEnabled;

    @Value("${spring.application.name}")
    private String serviceName;

    public HealthCheckController(DataSource dataSource,
            SseEventBus sseEventBus,
            AiEvolutionService aiEvolutionService) {
        this.dataSource = dataSource;
        this.sseEventBus = sseEventBus;
        this.aiEvolutionService = aiEvolutionService;
    }

    /**
     * Healthcheck avançado com status de componentes.
     * GET /api/v1/healthcheck
     */
    @GetMapping("/healthcheck")
    public ResponseEntity<Map<String, Object>> healthcheck() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", serviceName);
        response.put("status", "UP");

        // Status do banco de dados
        response.put("database", checkDatabase());

        // Status do SSE
        response.put("sse", checkSse());

        // Status das features
        response.put("features", checkFeatures());

        // Status do rate limit
        response.put("rateLimit", checkRateLimit());

        return ResponseEntity.ok(response);
    }

    /**
     * Verifica conexão com banco de dados.
     */
    private Map<String, Object> checkDatabase() {
        Map<String, Object> status = new LinkedHashMap<>();
        try (Connection conn = dataSource.getConnection()) {
            status.put("status", "UP");
            status.put("database", conn.getMetaData().getDatabaseProductName());
            status.put("version", conn.getMetaData().getDatabaseProductVersion());
        } catch (Exception e) {
            status.put("status", "DOWN");
            status.put("error", e.getMessage());
        }
        return status;
    }

    /**
     * Verifica status do SSE Event Bus.
     */
    private Map<String, Object> checkSse() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("status", "UP");
        status.put("totalConnections", sseEventBus.getTotalConnectionCount());
        return status;
    }

    /**
     * Verifica status das features.
     */
    private Map<String, Object> checkFeatures() {
        Map<String, Object> status = new LinkedHashMap<>();

        // AI feature
        Map<String, Object> aiStatus = new LinkedHashMap<>();
        aiStatus.put("enabled", aiEnabled);
        aiStatus.put("provider", aiEvolutionService.getProviderName());
        aiStatus.put("available", aiEvolutionService.isEngineAvailable());
        status.put("ai", aiStatus);

        // SSE feature (sempre ativa)
        Map<String, Object> sseStatus = new LinkedHashMap<>();
        sseStatus.put("enabled", true);
        status.put("sse", sseStatus);

        return status;
    }

    /**
     * Verifica status do rate limit.
     */
    private Map<String, Object> checkRateLimit() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("enabled", true);
        status.put("maxRequestsPerMinute", 60);
        status.put("excludedPaths", new String[] { "/stream", "/health", "/healthcheck" });
        return status;
    }
}
