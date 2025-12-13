package com.nexusartifex.api.dto;

/**
 * Response DTO para o endpoint de health check.
 */
public record HealthResponse(
        String status,
        String service) {
    public static HealthResponse up(String serviceName) {
        return new HealthResponse("UP", serviceName);
    }
}
