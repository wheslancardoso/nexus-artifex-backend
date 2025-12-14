package com.nexusartifex.api.dto.ai;

import com.nexusartifex.ai.engine.EvolutionResult;

import java.util.List;
import java.util.UUID;

/**
 * DTO de response para evolução criativa via IA.
 *
 * @param originalNodeId ID do node original
 * @param technique      Técnica SCAMPER aplicada
 * @param provider       Nome do provedor de IA usado
 * @param results        Lista de resultados de evolução
 */
public record AiEvolutionResponse(
        UUID originalNodeId,
        String technique,
        String provider,
        List<EvolutionResult> results) {

    /**
     * Factory method para criar response a partir dos resultados.
     */
    public static AiEvolutionResponse of(UUID originalNodeId, String technique,
            String provider, List<EvolutionResult> results) {
        return new AiEvolutionResponse(originalNodeId, technique, provider, results);
    }
}
