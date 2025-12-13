package com.nexusartifex.api.dto;

/**
 * Request DTO para evolução de nó usando SCAMPER.
 */
public record EvolutionRequest(
        String technique,
        Integer count) {
}
