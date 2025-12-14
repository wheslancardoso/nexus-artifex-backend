package com.nexusartifex.ai.service;

import com.nexusartifex.ai.engine.CreativeEvolutionEngine;
import com.nexusartifex.ai.engine.EvolutionContext;
import com.nexusartifex.ai.engine.EvolutionResult;
import com.nexusartifex.api.dto.ai.AiEvolutionRequest;
import com.nexusartifex.api.dto.ai.AiEvolutionResponse;
import com.nexusartifex.domain.model.ScamperTechnique;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import com.nexusartifex.shared.exceptions.NodeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Serviço de evolução criativa via IA.
 * Orquestra chamadas à engine de IA para gerar variações de nodes.
 */
@Service
public class AiEvolutionService {

    private static final Logger log = LoggerFactory.getLogger(AiEvolutionService.class);

    private final CreativeEvolutionEngine engine;
    private final NodeRepository nodeRepository;

    @Value("${features.ai.enabled:false}")
    private boolean aiEnabled;

    public AiEvolutionService(CreativeEvolutionEngine engine, NodeRepository nodeRepository) {
        this.engine = engine;
        this.nodeRepository = nodeRepository;
    }

    /**
     * Evolui um node usando IA com a técnica SCAMPER especificada.
     *
     * @param request Request contendo nodeId, técnica e count
     * @return Response com resultados da evolução
     */
    public AiEvolutionResponse evolve(AiEvolutionRequest request) {
        log.info("AI Evolution solicitada para node {} com técnica {} (enabled={})",
                request.nodeId(), request.technique(), aiEnabled);

        // Validar node existe
        var node = nodeRepository.findById(request.nodeId())
                .orElseThrow(() -> new NodeNotFoundException(request.nodeId()));

        // Converter technique
        ScamperTechnique technique = ScamperTechnique.valueOf(request.technique());

        // Se feature flag desativada, retornar resposta vazia
        if (!aiEnabled) {
            log.info("AI Evolution desativada via feature flag - retornando placeholder");
            return AiEvolutionResponse.of(
                    request.nodeId(),
                    technique.name(),
                    "disabled",
                    Collections.emptyList());
        }

        // Criar contexto para engine
        EvolutionContext context = new EvolutionContext(
                node.getId(),
                node.getProjectId(),
                technique,
                node.getLabel(),
                node.getSummary(),
                request.getCountOrDefault());

        // Chamar engine (atualmente NoOp - retorna lista vazia)
        List<EvolutionResult> results = engine.evolve(context);

        log.info("AI Evolution completada: {} resultados gerados pelo provedor '{}'",
                results.size(), engine.getProviderName());

        return AiEvolutionResponse.of(
                request.nodeId(),
                technique.name(),
                engine.getProviderName(),
                results);
    }

    /**
     * Verifica se a feature de IA está habilitada.
     */
    public boolean isAiEnabled() {
        return aiEnabled;
    }

    /**
     * Verifica se a engine de IA está disponível.
     */
    public boolean isEngineAvailable() {
        return engine.isAvailable();
    }

    /**
     * Retorna o nome do provedor de IA atual.
     */
    public String getProviderName() {
        return engine.getProviderName();
    }
}
