package com.nexusartifex.shared.exceptions;

/**
 * Exceção lançada quando há violação de regras de negócio na criação ou
 * manipulação de Project.
 */
public class InvalidProjectException extends RuntimeException {

    public InvalidProjectException(String message) {
        super(message);
    }

    public InvalidProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Factory method para nome inválido.
     */
    public static InvalidProjectException invalidName() {
        return new InvalidProjectException("O nome do projeto é obrigatório e não pode estar vazio");
    }

    /**
     * Factory method para nome muito longo.
     */
    public static InvalidProjectException nameTooLong(int maxLength) {
        return new InvalidProjectException("O nome do projeto não pode exceder " + maxLength + " caracteres");
    }
}
