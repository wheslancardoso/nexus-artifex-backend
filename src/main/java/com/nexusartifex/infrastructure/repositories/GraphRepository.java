package com.nexusartifex.infrastructure.repositories;

import com.nexusartifex.domain.model.Edge;

import java.util.List;
import java.util.UUID;

/**
 * Interface de repositório para persistência de Arestas do Grafo.
 */
public interface GraphRepository {

    /**
     * Salva uma aresta.
     * 
     * @param edge Aresta a salvar
     * @return Aresta salva
     */
    Edge save(Edge edge);

    /**
     * Salva uma aresta com projectId explícito.
     * 
     * @param edge      Aresta a salvar
     * @param projectId ID do projeto
     * @return Aresta salva
     */
    Edge saveWithProject(Edge edge, UUID projectId);

    /**
     * Salva múltiplas arestas.
     * 
     * @param edges Lista de arestas a salvar
     * @return Lista de arestas salvas
     */
    List<Edge> saveAll(List<Edge> edges);

    /**
     * Lista todas as arestas de um projeto.
     * 
     * @param projectId ID do projeto
     * @return Lista de arestas do projeto
     */
    List<Edge> findByProjectId(UUID projectId);

    /**
     * Busca arestas que partem de um nó específico.
     * 
     * @param nodeId ID do nó de origem
     * @return Lista de arestas
     */
    List<Edge> findBySourceNodeId(UUID nodeId);

    /**
     * Busca arestas que chegam a um nó específico.
     * 
     * @param nodeId ID do nó de destino
     * @return Lista de arestas
     */
    List<Edge> findByTargetNodeId(UUID nodeId);

    /**
     * Remove todas as arestas de um projeto.
     * 
     * @param projectId ID do projeto
     */
    void deleteByProjectId(UUID projectId);
}
