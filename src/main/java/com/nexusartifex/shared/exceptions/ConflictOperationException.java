package com.nexusartifex.shared.exceptions;

import java.util.UUID;

/**
 * Exceção lançada quando há conflito em operação (ex: Edge duplicada).
 * Mapeada para HTTP 409 Conflict.
 */
public class ConflictOperationException extends RuntimeException {

    public ConflictOperationException(String message) {
        super(message);
    }

    public ConflictOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Factory method para edge duplicada.
     */
    public static ConflictOperationException duplicateEdge(UUID source, UUID target) {
        return new ConflictOperationException(
                "Conflito: Edge duplicada - já existe uma conexão de " + source + " para " + target);
    }
}
