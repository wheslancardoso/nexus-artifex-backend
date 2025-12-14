package com.nexusartifex.shared.exceptions;

/**
 * Exceção lançada quando há violação de regras de negócio na criação ou
 * manipulação de Node.
 */
public class InvalidNodeException extends RuntimeException {

    public InvalidNodeException(String message) {
        super(message);
    }

    public InvalidNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Factory method para projectId obrigatório.
     */
    public static InvalidNodeException projectIdRequired() {
        return new InvalidNodeException("O projectId é obrigatório para criar um nó");
    }

    /**
     * Factory method para type obrigatório.
     */
    public static InvalidNodeException typeRequired() {
        return new InvalidNodeException("O type do nó é obrigatório");
    }

    /**
     * Factory method para label obrigatório.
     */
    public static InvalidNodeException labelRequired() {
        return new InvalidNodeException("O label do nó é obrigatório e não pode estar vazio");
    }

    /**
     * Factory method para label muito longo.
     */
    public static InvalidNodeException labelTooLong(int maxLength) {
        return new InvalidNodeException("O label do nó não pode exceder " + maxLength + " caracteres");
    }
}
