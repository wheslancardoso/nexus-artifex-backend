package com.nexusartifex.api.dto;

/**
 * Request DTO para criação de nó.
 */
public record CreateNodeRequest(
        String type,
        String label,
        String summary) {
}
