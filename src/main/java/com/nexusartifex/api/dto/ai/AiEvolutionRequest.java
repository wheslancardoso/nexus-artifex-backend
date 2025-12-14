package com.nexusartifex.api.dto.ai;

import java.util.UUID;

/**
 * DTO de request para evolução criativa via IA.
 *
 * @param nodeId    ID do node a evoluir
 * @param technique Técnica SCAMPER a aplicar (SUBSTITUTE, COMBINE, etc)
 * @param count     Número de variações a gerar (default: 1)
 */
public record AiEvolutionRequest(
        UUID nodeId,
        String technique,
        Integer count) {

    /**
     * Retorna count com valor default de 1 se null.
     */
    public int getCountOrDefault() {
        return count != null ? count : 1;
    }
}
