-- V2__create_nodes_table.sql
-- Criação da tabela de nós do grafo criativo

CREATE TABLE nodes (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    label VARCHAR(255) NOT NULL,
    summary VARCHAR(1000),
    visual_data JSONB
);

-- Indexes
CREATE INDEX idx_nodes_project_id ON nodes(project_id);
CREATE INDEX idx_nodes_type ON nodes(type);
