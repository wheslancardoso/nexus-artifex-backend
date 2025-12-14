package com.nexusartifex.ai.engine;

import java.util.List;

/**
 * Interface para engine de evolução criativa.
 * Abstração para integração com diferentes provedores de IA (LangChain4j,
 * OpenAI, etc).
 */
public interface CreativeEvolutionEngine {

    /**
     * Evolui um conceito usando a técnica SCAMPER especificada.
     * 
     * @param context Contexto contendo node original e técnica a aplicar
     * @return Lista de resultados de evolução (labels e summaries gerados)
     */
    List<EvolutionResult> evolve(EvolutionContext context);

    /**
     * Verifica se a engine está configurada e pronta para uso.
     * 
     * @return true se a engine está disponível
     */
    default boolean isAvailable() {
        return true;
    }

    /**
     * Retorna o nome do provedor de IA sendo usado.
     * 
     * @return Nome do provedor (ex: "OpenAI", "LangChain4j", "NoOp")
     */
    String getProviderName();
}
