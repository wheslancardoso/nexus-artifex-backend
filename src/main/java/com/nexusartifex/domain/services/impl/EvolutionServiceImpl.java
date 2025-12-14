package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Evolution;
import com.nexusartifex.domain.model.Node;
import com.nexusartifex.domain.model.NodeType;
import com.nexusartifex.domain.model.ScamperTechnique;
import com.nexusartifex.domain.services.EvolutionService;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import com.nexusartifex.shared.exceptions.InvalidEvolutionException;
import com.nexusartifex.shared.exceptions.NodeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço de Evolução criativa com técnicas SCAMPER.
 */
@Service
public class EvolutionServiceImpl implements EvolutionService {

    private final NodeRepository nodeRepository;

    public EvolutionServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Evolution evolve(UUID nodeId, ScamperTechnique technique, int count) {
        // Regra 1: nodeId obrigatório
        if (nodeId == null) {
            throw InvalidEvolutionException.nodeIdRequired();
        }

        // Regra 2: técnica obrigatória
        if (technique == null) {
            throw InvalidEvolutionException.techniqueRequired();
        }

        // Regra 3: node base deve existir
        Node originalNode = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new NodeNotFoundException(nodeId));

        // Gerar 1 novo Node derivado (sem IA, usando placeholder baseado na técnica)
        Node evolvedNode = createEvolvedNode(originalNode, technique);

        // Persistir o node evoluído
        Node savedNode = nodeRepository.save(evolvedNode);

        // Retornar Evolution com lista contendo o node gerado
        return new Evolution(nodeId, technique, List.of(savedNode));
    }

    /**
     * Cria um novo Node derivado baseado na técnica SCAMPER.
     * Esta é uma implementação placeholder sem IA.
     */
    private Node createEvolvedNode(Node originalNode, ScamperTechnique technique) {
        UUID id = UUID.randomUUID();
        UUID projectId = originalNode.getProjectId();
        NodeType type = NodeType.MUTATION;

        String label = generateLabel(originalNode.getLabel(), technique);
        String summary = generateSummary(originalNode.getSummary(), technique);

        return new Node(id, projectId, type, label, summary, Collections.emptyMap());
    }

    private String generateLabel(String originalLabel, ScamperTechnique technique) {
        return switch (technique) {
            case SUBSTITUTE -> "[Substituir] " + originalLabel;
            case COMBINE -> "[Combinar] " + originalLabel;
            case ADAPT -> "[Adaptar] " + originalLabel;
            case MODIFY -> "[Modificar] " + originalLabel;
            case PUT_TO_ANOTHER_USE -> "[Outro Uso] " + originalLabel;
            case ELIMINATE -> "[Eliminar] " + originalLabel;
            case REVERSE -> "[Inverter] " + originalLabel;
        };
    }

    private String generateSummary(String originalSummary, ScamperTechnique technique) {
        String base = originalSummary != null ? originalSummary : "Ideia original";
        return switch (technique) {
            case SUBSTITUTE -> "E se substituíssemos parte de: " + base + "?";
            case COMBINE -> "E se combinássemos com outra ideia: " + base + "?";
            case ADAPT -> "E se adaptássemos para outro contexto: " + base + "?";
            case MODIFY -> "E se modificássemos/amplificássemos: " + base + "?";
            case PUT_TO_ANOTHER_USE -> "E se usássemos para outro propósito: " + base + "?";
            case ELIMINATE -> "E se eliminássemos parte de: " + base + "?";
            case REVERSE -> "E se invertêssemos/reorganizássemos: " + base + "?";
        };
    }
}
