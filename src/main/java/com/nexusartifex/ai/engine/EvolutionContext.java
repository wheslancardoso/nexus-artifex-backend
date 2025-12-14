package com.nexusartifex.ai.engine;

import com.nexusartifex.domain.model.ScamperTechnique;

import java.util.UUID;

/**
 * Contexto para evolução criativa via IA.
 * Contém todas as informações necessárias para gerar nodes evoluídos.
 *
 * @param nodeId    ID do node original
 * @param projectId ID do projeto
 * @param technique Técnica SCAMPER a aplicar
 * @param label     Label do node original
 * @param summary   Summary do node original (pode ser null)
 * @param count     Número de variações a gerar
 */
public record EvolutionContext(
        UUID nodeId,
        UUID projectId,
        ScamperTechnique technique,
        String label,
        String summary,
        int count) {

    /**
     * Factory method para criar contexto com count padrão de 1.
     */
    public static EvolutionContext of(UUID nodeId, UUID projectId,
            ScamperTechnique technique,
            String label, String summary) {
        return new EvolutionContext(nodeId, projectId, technique, label, summary, 1);
    }
}
