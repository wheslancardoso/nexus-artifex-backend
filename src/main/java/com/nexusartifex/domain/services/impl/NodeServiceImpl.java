package com.nexusartifex.domain.services.impl;

import com.nexusartifex.domain.model.Node;
import com.nexusartifex.domain.model.NodeType;
import com.nexusartifex.domain.services.NodeService;
import com.nexusartifex.infrastructure.repositories.NodeRepository;
import com.nexusartifex.infrastructure.repositories.ProjectRepository;
import com.nexusartifex.shared.exceptions.InvalidNodeException;
import com.nexusartifex.shared.exceptions.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public NodeServiceImpl(NodeRepository nodeRepository, ProjectRepository projectRepository) {
        this.nodeRepository = nodeRepository;
        this.projectRepository = projectRepository;
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
        return nodeRepository.save(node);
    }

    @Override
    public Optional<Node> findById(UUID nodeId) {
        return nodeRepository.findById(nodeId);
    }
}
