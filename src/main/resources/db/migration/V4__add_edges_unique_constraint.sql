-- V4: Adiciona constraint UNIQUE para evitar edges duplicadas
-- Garante integridade referencial: não pode haver duas edges com mesmo source + target

ALTER TABLE edges
ADD CONSTRAINT uk_edges_source_target UNIQUE (source_node_id, target_node_id);
