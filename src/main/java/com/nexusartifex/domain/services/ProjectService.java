package com.nexusartifex.domain.services;

import com.nexusartifex.domain.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface de serviço de domínio para Projetos.
 */
public interface ProjectService {

    /**
     * Cria um novo projeto.
     * 
     * @param name Nome do projeto
     * @return Projeto criado
     */
    Project create(String name);

    /**
     * Lista todos os projetos.
     * 
     * @return Lista de projetos
     */
    List<Project> findAll();

    /**
     * Busca projeto por ID.
     * 
     * @param id ID do projeto
     * @return Projeto encontrado ou vazio
     */
    Optional<Project> findById(UUID id);
}
