package com.nexusartifex.infrastructure.persistence.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexusartifex.domain.model.Node;
import com.nexusartifex.domain.model.NodeType;
import com.nexusartifex.infrastructure.persistence.entity.NodeEntity;

import java.util.Collections;
import java.util.Map;

/**
 * Mapper para conversão entre Node (domain) e NodeEntity (persistence).
 */
public final class NodeMapper {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private NodeMapper() {
        // Utility class
    }

    /**
     * Converte Domain para Entity.
     */
    public static NodeEntity toEntity(Node domain) {
        if (domain == null) {
            return null;
        }
        return new NodeEntity(
                domain.getId(),
                domain.getProjectId(),
                domain.getType() != null ? domain.getType().name() : null,
                domain.getLabel(),
                domain.getSummary(),
                toJsonString(domain.getVisualData()));
    }

    /**
     * Converte Entity para Domain.
     */
    public static Node toDomain(NodeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Node(
                entity.getId(),
                entity.getProjectId(),
                entity.getType() != null ? NodeType.valueOf(entity.getType()) : null,
                entity.getLabel(),
                entity.getSummary(),
                fromJsonString(entity.getVisualData()));
    }

    private static String toJsonString(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return JSON_MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private static Map<String, Object> fromJsonString(String json) {
        if (json == null || json.isBlank()) {
            return Collections.emptyMap();
        }
        try {
            return JSON_MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            return Collections.emptyMap();
        }
    }
}
