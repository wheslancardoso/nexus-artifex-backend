-- V1__create_projects_table.sql
-- Criação da tabela de projetos

CREATE TABLE projects (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Index para ordenação por data
CREATE INDEX idx_projects_created_at ON projects(created_at DESC);
