package com.nexusartifex.domain.model;

import java.util.List;
import java.util.UUID;

/**
 * Model de domínio para resultado de Evolução criativa.
 */
public class Evolution {

    private UUID originalNodeId;
    private ScamperTechnique technique;
    private List<Node> generatedNodes;

    public Evolution() {
    }

    public Evolution(UUID originalNodeId, ScamperTechnique technique, List<Node> generatedNodes) {
        this.originalNodeId = originalNodeId;
        this.technique = technique;
        this.generatedNodes = generatedNodes;
    }

    public UUID getOriginalNodeId() {
        return originalNodeId;
    }

    public void setOriginalNodeId(UUID originalNodeId) {
        this.originalNodeId = originalNodeId;
    }

    public ScamperTechnique getTechnique() {
        return technique;
    }

    public void setTechnique(ScamperTechnique technique) {
        this.technique = technique;
    }

    public List<Node> getGeneratedNodes() {
        return generatedNodes;
    }

    public void setGeneratedNodes(List<Node> generatedNodes) {
        this.generatedNodes = generatedNodes;
    }
}
