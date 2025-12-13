package com.nexusartifex.api.dto.request;

/**
 * Request DTO para evolução de nó usando SCAMPER.
 * 
 * @see components/schemas/EvolutionRequest no OpenAPI
 */
public record EvolutionRequest(
        String technique,
        Integer count) {
}
