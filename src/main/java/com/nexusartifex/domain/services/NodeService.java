package com.nexusartifex.domain.services;

import com.nexusartifex.domain.model.Node;
import com.nexusartifex.domain.model.NodeType;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface de serviço de domínio para Nós.
 */
public interface NodeService {

    /**
     * Cria um novo nó em um projeto.
     * 
     * @param projectId ID do projeto
     * @param type      Tipo do nó (CONCEPT ou MUTATION)
     * @param label     Rótulo do nó
     * @param summary   Resumo do nó
     * @return Nó criado
     */
    Node create(UUID projectId, NodeType type, String label, String summary);

    /**
     * Busca nó por ID.
     * 
     * @param nodeId ID do nó
     * @return Nó encontrado ou vazio
     */
    Optional<Node> findById(UUID nodeId);
}
