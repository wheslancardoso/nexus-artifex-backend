package com.nexusartifex.domain.services;

import com.nexusartifex.domain.model.Node;
import com.nexusartifex.domain.model.NodeType;
import com.nexusartifex.domain.model.ScamperTechnique;
import com.nexusartifex.shared.exceptions.InvalidEvolutionException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para regras de domínio de Evolution.
 */
class EvolutionDomainTest {

    @Test
    void scamperTechnique_shouldHaveAllValues() {
        ScamperTechnique[] techniques = ScamperTechnique.values();
        assertEquals(7, techniques.length, "SCAMPER deve ter 7 técnicas");
    }

    @Test
    void scamperTechnique_substitute_shouldExist() {
        ScamperTechnique technique = ScamperTechnique.valueOf("SUBSTITUTE");
        assertNotNull(technique);
    }

    @Test
    void invalidEvolution_nodeIdRequired_shouldHaveCorrectMessage() {
        InvalidEvolutionException ex = InvalidEvolutionException.nodeIdRequired();
        assertTrue(ex.getMessage().toLowerCase().contains("node"));
    }

    @Test
    void invalidEvolution_techniqueRequired_shouldHaveCorrectMessage() {
        InvalidEvolutionException ex = InvalidEvolutionException.techniqueRequired();
        assertTrue(ex.getMessage().toLowerCase().contains("técnica") ||
                ex.getMessage().toLowerCase().contains("tecnica"));
    }

    @Test
    void node_mutationType_shouldBeValid() {
        Node node = new Node(UUID.randomUUID(), UUID.randomUUID(),
                NodeType.MUTATION, "Mutação", "Resumo", null);
        assertEquals(NodeType.MUTATION, node.getType());
    }
}
