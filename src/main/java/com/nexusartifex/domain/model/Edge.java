package com.nexusartifex.domain.model;

import java.util.UUID;

/**
 * Model de domínio para Aresta do grafo.
 */
public class Edge {

    private UUID source;
    private UUID target;
    private String relationship;

    public Edge() {
    }

    public Edge(UUID source, UUID target, String relationship) {
        this.source = source;
        this.target = target;
        this.relationship = relationship;
    }

    public UUID getSource() {
        return source;
    }

    public void setSource(UUID source) {
        this.source = source;
    }

    public UUID getTarget() {
        return target;
    }

    public void setTarget(UUID target) {
        this.target = target;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
