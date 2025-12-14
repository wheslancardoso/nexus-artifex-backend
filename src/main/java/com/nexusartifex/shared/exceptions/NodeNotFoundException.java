package com.nexusartifex.shared.exceptions;

import java.util.UUID;

/**
 * Exceção lançada quando um Node não é encontrado.
 */
public class NodeNotFoundException extends RuntimeException {

    private final UUID nodeId;

    public NodeNotFoundException(UUID nodeId) {
        super("Nó não encontrado: " + nodeId);
        this.nodeId = nodeId;
    }

    public NodeNotFoundException(String message) {
        super(message);
        this.nodeId = null;
    }

    public UUID getNodeId() {
        return nodeId;
    }
}
