package com.nexusartifex.domain.services;

import com.nexusartifex.domain.model.Graph;

import java.util.UUID;

/**
 * Interface de serviço de domínio para Grafo do projeto.
 */
public interface GraphService {

    /**
     * Obtém o grafo completo de um projeto.
     * 
     * @param projectId ID do projeto
     * @return Grafo com nós e arestas
     */
    Graph getProjectGraph(UUID projectId);
}
