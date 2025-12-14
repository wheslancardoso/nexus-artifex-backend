package com.nexusartifex.ai.engine;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Implementação NoOp (No Operation) da engine de evolução criativa.
 * Retorna lista vazia - usada como fallback ou para testes.
 */
@Component
public class NoOpCreativeEvolutionEngine implements CreativeEvolutionEngine {

    @Override
    public List<EvolutionResult> evolve(EvolutionContext context) {
        // Retorna lista vazia - sem integração com IA
        return Collections.emptyList();
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getProviderName() {
        return "NoOp";
    }
}
