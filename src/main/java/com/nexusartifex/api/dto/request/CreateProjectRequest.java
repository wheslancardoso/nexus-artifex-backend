package com.nexusartifex.api.dto.request;

/**
 * Request DTO para criação de projeto.
 * 
 * @see components/schemas/CreateProjectRequest no OpenAPI
 */
public record CreateProjectRequest(
        String name) {
}
