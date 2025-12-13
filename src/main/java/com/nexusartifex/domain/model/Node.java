package com.nexusartifex.domain.model;

import java.util.Map;
import java.util.UUID;

/**
 * Model de domínio para Nó do grafo criativo.
 */
public class Node {

    private UUID id;
    private UUID projectId;
    private NodeType type;
    private String label;
    private String summary;
    private Map<String, Object> visualData;

    public Node() {
    }

    public Node(UUID id, UUID projectId, NodeType type, String label, String summary, Map<String, Object> visualData) {
        this.id = id;
        this.projectId = projectId;
        this.type = type;
        this.label = label;
        this.summary = summary;
        this.visualData = visualData;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Map<String, Object> getVisualData() {
        return visualData;
    }

    public void setVisualData(Map<String, Object> visualData) {
        this.visualData = visualData;
    }
}
