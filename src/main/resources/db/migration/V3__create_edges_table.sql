-- V3__create_edges_table.sql
-- Criação da tabela de arestas do grafo

CREATE TABLE edges (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    project_id UUID NOT NULL,
    source_node_id UUID NOT NULL,
    target_node_id UUID NOT NULL,
    relationship VARCHAR(100)
);

-- Indexes
CREATE INDEX idx_edges_project_id ON edges(project_id);
CREATE INDEX idx_edges_source_node_id ON edges(source_node_id);
CREATE INDEX idx_edges_target_node_id ON edges(target_node_id);
