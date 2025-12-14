package com.nexusartifex.infrastructure.sse;

/**
 * Constantes para nomes de eventos SSE.
 * Garante consistência nos nomes de eventos em todo o sistema.
 */
public final class SseEventTypes {

    private SseEventTypes() {
        // Utility class, não instanciar
    }

    /**
     * Evento emitido quando conexão SSE é estabelecida.
     */
    public static final String CONNECTED = "connected";

    /**
     * Evento emitido quando um Node é criado manualmente.
     */
    public static final String NODE_CREATED = "node.created";

    /**
     * Evento emitido quando um Node é evoluído via SCAMPER.
     */
    public static final String NODE_EVOLVED = "node.evolved";
}
