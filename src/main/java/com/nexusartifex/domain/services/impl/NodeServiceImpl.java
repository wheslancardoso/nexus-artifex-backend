package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Node;
import com.nexusartifex.domain.model.NodeType;
import com.nexusartifex.domain.services.NodeService;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import com.nexusartifex.infrastructure.repositories.ProjectRepository;
import com.nexusartifex.infrastructure.sse.SseEventBus;
import com.nexusartifex.shared.exceptions.InvalidNodeException;
import com.nexusartifex.shared.exceptions.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do serviço de Nós com regras de negócio.
 */
@Service
public class NodeServiceImpl implements NodeService {

    private static final int MAX_LABEL_LENGTH = 255;

    private final NodeRepository nodeRepository;
    private final ProjectRepository projectRepository;
    private final SseEventBus sseEventBus;

    public NodeServiceImpl(NodeRepository nodeRepository,
            ProjectRepository projectRepository,
            SseEventBus sseEventBus) {
        this.nodeRepository = nodeRepository;
        this.projectRepository = projectRepository;
        this.sseEventBus = sseEventBus;
    }

    @Override
    public Node create(UUID projectId, NodeType type, String label, String summary) {
        // Regra 1: projectId obrigatório
        if (projectId == null) {
            throw InvalidNodeException.projectIdRequired();
        }

        // Regra 2: projectId deve existir
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }

        // Regra 3: type obrigatório
        if (type == null) {
            throw InvalidNodeException.typeRequired();
        }

        // Regra 4: label obrigatório
        if (label == null || label.isBlank()) {
            throw InvalidNodeException.labelRequired();
        }

        // Regra 5: label não pode exceder limite
        String trimmedLabel = label.trim();
        if (trimmedLabel.length() > MAX_LABEL_LENGTH) {
            throw InvalidNodeException.labelTooLong(MAX_LABEL_LENGTH);
        }

        // Regra 6: Geração de UUID no service
        UUID id = UUID.randomUUID();

        // Trim do summary se presente
        String trimmedSummary = summary != null ? summary.trim() : null;

        var node = new Node(id, projectId, type, trimmedLabel, trimmedSummary, Collections.emptyMap());
        Node savedNode = nodeRepository.save(node);

        // Emitir evento SSE para o projeto
        publishNodeCreatedEvent(savedNode);

        return savedNode;
    }

    /**
     * Publica evento SSE "node.created" para todas as conexões do projeto.
     */
    private void publishNodeCreatedEvent(Node node) {
        Map<String, Object> payload = Map.of(
                "type", "node.created",
                "projectId", node.getProjectId().toString(),
                "node", Map.of(
                        "id", node.getId().toString(),
                        "type", node.getType().name(),
                        "label", node.getLabel(),
                        "summary", node.getSummary() != null ? node.getSummary() : ""));
        sseEventBus.publish(node.getProjectId(), "node.created", payload);
    }

    @Override
    public Optional<Node> findById(UUID nodeId) {
        return nodeRepository.findById(nodeId);
    }
}
