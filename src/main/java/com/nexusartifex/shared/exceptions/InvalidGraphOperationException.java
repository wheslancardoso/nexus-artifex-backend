package com.nexusartifex.shared.exceptions;

import java.util.UUID;

/**
 * Exceção lançada quando há violação de regras de consistência do Grafo.
 */
public class InvalidGraphOperationException extends RuntimeException {

    public InvalidGraphOperationException(String message) {
        super(message);
    }

    public InvalidGraphOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Factory method para edge duplicada.
     */
    public static InvalidGraphOperationException duplicateEdge(UUID source, UUID target) {
        return new InvalidGraphOperationException(
                "Edge duplicada: já existe uma conexão de " + source + " para " + target);
    }

    /**
     * Factory method para nodes de projetos diferentes.
     */
    public static InvalidGraphOperationException differentProjects() {
        return new InvalidGraphOperationException(
                "Não é possível criar edge entre nodes de projetos diferentes");
    }

    /**
     * Factory method para self-loop.
     */
    public static InvalidGraphOperationException selfLoop(UUID nodeId) {
        return new InvalidGraphOperationException(
                "Não é permitido criar edge de um nó para si mesmo: " + nodeId);
    }
}
