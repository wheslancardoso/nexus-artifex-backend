package com.nexusartifex.api.dto.request;

/**
 * Request DTO para criação de nó.
 * 
 * @see components/schemas/CreateNodeRequest no OpenAPI
 */
public record CreateNodeRequest(
        String type,
        String label,
        String summary) {
}
