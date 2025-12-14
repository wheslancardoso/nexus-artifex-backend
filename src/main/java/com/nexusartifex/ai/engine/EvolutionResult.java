package com.nexusartifex.ai.engine;

/**
 * Resultado de uma evolução criativa gerada pela IA.
 * Representa um único node evoluído.
 *
 * @param label   Label gerado para o node evoluído
 * @param summary Summary gerado explicando a evolução
 */
public record EvolutionResult(
        String label,
        String summary) {
}
