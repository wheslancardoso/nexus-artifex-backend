package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Edge;
import com.nexusartifex.domain.model.Evolution;
import com.nexusartifex.domain.model.Node;
import com.nexusartifex.domain.model.NodeType;
import com.nexusartifex.domain.model.ScamperTechnique;
import com.nexusartifex.domain.services.EvolutionService;
import com.nexusartifex.infrastructure.repositories.GraphRepository;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import com.nexusartifex.infrastructure.sse.SseEventBus;
import com.nexusartifex.shared.exceptions.ConflictOperationException;
import com.nexusartifex.shared.exceptions.InvalidEvolutionException;
import com.nexusartifex.shared.exceptions.InvalidGraphOperationException;
import com.nexusartifex.shared.exceptions.NodeNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implementação do serviço de Evolução criativa com técnicas SCAMPER.
 */
@Service
public class EvolutionServiceImpl implements EvolutionService {

    private final NodeRepository nodeRepository;
    private final GraphRepository graphRepository;
    private final SseEventBus sseEventBus;

    public EvolutionServiceImpl(NodeRepository nodeRepository,
            GraphRepository graphRepository,
            SseEventBus sseEventBus) {
        this.nodeRepository = nodeRepository;
        this.graphRepository = graphRepository;
        this.sseEventBus = sseEventBus;
    }

    /**
     * Evolui um node usando técnica SCAMPER.
     * 
     * Transacional para garantir atomicidade: Node + Edge são persistidos juntos.
     * Se ocorrer exceção, todo o trabalho é revertido e nenhum evento SSE é
     * emitido.
     */
    @Override
    @Transactional
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

        // Criar Edge e validar consistência
        Edge edge = createEvolutionEdge(originalNode, savedNode, technique);

        // Validação de consistência: source != target (self-loop)
        if (originalNode.getId().equals(savedNode.getId())) {
            throw InvalidGraphOperationException.selfLoop(originalNode.getId());
        }

        // Validação de consistência: nodes do mesmo projeto
        if (!originalNode.getProjectId().equals(savedNode.getProjectId())) {
            throw InvalidGraphOperationException.differentProjects();
        }

        // Validação de consistência: edge duplicada → HTTP 409 (check otimista)
        if (graphRepository.existsEdge(originalNode.getId(), savedNode.getId())) {
            throw ConflictOperationException.duplicateEdge(originalNode.getId(), savedNode.getId());
        }

        // Persistir a Edge com tratamento de violação de constraint (concorrência)
        try {
            graphRepository.saveWithProject(edge, originalNode.getProjectId());
        } catch (DataIntegrityViolationException e) {
            // Constraint UNIQUE violada por request concorrente
            throw ConflictOperationException.duplicateEdge(originalNode.getId(), savedNode.getId());
        }

        // Emitir evento SSE para o projeto
        publishNodeEvolvedEvent(originalNode.getProjectId(), savedNode, technique, edge);

        // Retornar Evolution com lista contendo o node gerado
        return new Evolution(nodeId, technique, List.of(savedNode));
    }

    /**
     * Publica evento SSE "node.evolved" para todas as conexões do projeto.
     */
    private void publishNodeEvolvedEvent(UUID projectId, Node node, ScamperTechnique technique, Edge edge) {
        Map<String, Object> payload = Map.of(
                "type", "node.evolved",
                "projectId", projectId.toString(),
                "technique", technique.name(),
                "node", Map.of(
                        "id", node.getId().toString(),
                        "label", node.getLabel(),
                        "summary", node.getSummary() != null ? node.getSummary() : ""),
                "edge", Map.of(
                        "source", edge.getSource().toString(),
                        "target", edge.getTarget().toString(),
                        "relationship", edge.getRelationship()));
        sseEventBus.publish(projectId, "node.evolved", payload);
    }

    /**
     * Cria um novo Node derivado baseado na técnica SCAMPER.
     */
    private Node createEvolvedNode(Node originalNode, ScamperTechnique technique) {
        UUID id = UUID.randomUUID();
        UUID projectId = originalNode.getProjectId();
        NodeType type = NodeType.MUTATION;

        String label = generateLabel(originalNode.getLabel(), technique);
        String summary = generateSummary(originalNode.getSummary(), technique);

        return new Node(id, projectId, type, label, summary, Collections.emptyMap());
    }

    /**
     * Cria uma Edge conectando o node original ao node evoluído.
     */
    private Edge createEvolutionEdge(Node originalNode, Node evolvedNode, ScamperTechnique technique) {
        UUID source = originalNode.getId();
        UUID target = evolvedNode.getId();
        String relationship = "EVOLVED_" + technique.name();

        return new Edge(source, target, relationship);
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
