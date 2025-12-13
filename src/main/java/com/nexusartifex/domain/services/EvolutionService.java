package com.nexusartifex.domain.services;

import com.nexusartifex.domain.model.Evolution;
import com.nexusartifex.domain.model.ScamperTechnique;

import java.util.UUID;

/**
 * Interface de serviço de domínio para Evolução criativa (SCAMPER).
 */
public interface EvolutionService {

    /**
     * Evolui um nó usando uma técnica SCAMPER.
     * 
     * @param nodeId    ID do nó a evoluir
     * @param technique Técnica SCAMPER a aplicar
     * @param count     Quantidade de variações a gerar
     * @return Resultado da evolução com nós gerados
     */
    Evolution evolve(UUID nodeId, ScamperTechnique technique, int count);
}
