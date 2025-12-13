package com.nexusartifex.infrastructure.repositories;

import com.nexusartifex.domain.model.Node;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface de repositório para persistência de Nós.
 */
public interface NodeRepository {

    /**
     * Salva um nó.
     * 
     * @param node Nó a salvar
     * @return Nó salvo
     */
    Node save(Node node);

    /**
     * Salva múltiplos nós.
     * 
     * @param nodes Lista de nós a salvar
     * @return Lista de nós salvos
     */
    List<Node> saveAll(List<Node> nodes);

    /**
     * Busca nó por ID.
     * 
     * @param id ID do nó
     * @return Nó encontrado ou vazio
     */
    Optional<Node> findById(UUID id);

    /**
     * Lista todos os nós de um projeto.
     * 
     * @param projectId ID do projeto
     * @return Lista de nós do projeto
     */
    List<Node> findByProjectId(UUID projectId);

    /**
     * Verifica se nó existe por ID.
     * 
     * @param id ID do nó
     * @return true se existe
     */
    boolean existsById(UUID id);

    /**
     * Remove nó por ID.
     * 
     * @param id ID do nó
     */
    void deleteById(UUID id);
}
