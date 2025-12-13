package com.nexusartifex.infrastructure.repositories;

import com.nexusartifex.domain.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface de repositório para persistência de Projetos.
 */
public interface ProjectRepository {

    /**
     * Salva um projeto.
     * 
     * @param project Projeto a salvar
     * @return Projeto salvo
     */
    Project save(Project project);

    /**
     * Busca projeto por ID.
     * 
     * @param id ID do projeto
     * @return Projeto encontrado ou vazio
     */
    Optional<Project> findById(UUID id);

    /**
     * Lista todos os projetos.
     * 
     * @return Lista de projetos
     */
    List<Project> findAll();

    /**
     * Verifica se projeto existe por ID.
     * 
     * @param id ID do projeto
     * @return true se existe
     */
    boolean existsById(UUID id);

    /**
     * Remove projeto por ID.
     * 
     * @param id ID do projeto
     */
    void deleteById(UUID id);
}
