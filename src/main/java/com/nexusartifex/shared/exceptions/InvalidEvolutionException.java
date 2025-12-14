package com.nexusartifex.shared.exceptions;

/**
 * Exceção lançada quando há violação de regras de negócio na evolução de Node.
 */
public class InvalidEvolutionException extends RuntimeException {

    public InvalidEvolutionException(String message) {
        super(message);
    }

    public InvalidEvolutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Factory method para técnica obrigatória.
     */
    public static InvalidEvolutionException techniqueRequired() {
        return new InvalidEvolutionException("A técnica de evolução (SCAMPER) é obrigatória");
    }

    /**
     * Factory method para técnica inválida.
     */
    public static InvalidEvolutionException invalidTechnique(String technique) {
        return new InvalidEvolutionException("Técnica de evolução inválida: " + technique +
                ". Use: SUBSTITUTE, COMBINE, ADAPT, MODIFY, PUT_TO_OTHER_USES, ELIMINATE, REVERSE");
    }

    /**
     * Factory method para nodeId obrigatório.
     */
    public static InvalidEvolutionException nodeIdRequired() {
        return new InvalidEvolutionException("O nodeId é obrigatório para evoluir um nó");
    }
}
