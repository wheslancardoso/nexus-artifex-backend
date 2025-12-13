package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Evolution;
import com.nexusartifex.domain.model.ScamperTechnique;
import com.nexusartifex.domain.services.EvolutionService;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

/**
 * Implementação placeholder do serviço de Evolução criativa.
 */
@Service
public class EvolutionServiceImpl implements EvolutionService {

    private final NodeRepository nodeRepository;

    public EvolutionServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Evolution evolve(UUID nodeId, ScamperTechnique technique, int count) {
        // Placeholder: retorna evolução vazia
        return new Evolution(nodeId, technique, Collections.emptyList());
    }
}
