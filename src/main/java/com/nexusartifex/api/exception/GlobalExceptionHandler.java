package com.nexusartifex.api.exception;

import com.nexusartifex.api.dto.response.ErrorResponse;
import com.nexusartifex.shared.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler global para exceções de domínio.
 * Mapeia exceções para respostas HTTP apropriadas com ErrorResponse
 * padronizado.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ========== Códigos de Erro Estáveis ==========
    private static final String CODE_INVALID_PROJECT = "INVALID_PROJECT";
    private static final String CODE_INVALID_NODE = "INVALID_NODE";
    private static final String CODE_INVALID_EVOLUTION = "INVALID_EVOLUTION";
    private static final String CODE_INVALID_GRAPH_OPERATION = "INVALID_GRAPH_OPERATION";
    private static final String CODE_PROJECT_NOT_FOUND = "PROJECT_NOT_FOUND";
    private static final String CODE_NODE_NOT_FOUND = "NODE_NOT_FOUND";
    private static final String CODE_INVALID_REQUEST = "INVALID_REQUEST";
    private static final String CODE_MISSING_REQUIRED_VALUE = "MISSING_REQUIRED_VALUE";

    // ========== 400 Bad Request ==========

    @ExceptionHandler(InvalidProjectException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProject(
            InvalidProjectException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(CODE_INVALID_PROJECT, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidNodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidNode(
            InvalidNodeException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(CODE_INVALID_NODE, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidEvolutionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEvolution(
            InvalidEvolutionException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(CODE_INVALID_EVOLUTION, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidGraphOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidGraphOperation(
            InvalidGraphOperationException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(CODE_INVALID_GRAPH_OPERATION, ex.getMessage(), request.getRequestURI()));
    }

    // ========== 404 Not Found ==========

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFound(
            ProjectNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(CODE_PROJECT_NOT_FOUND, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(NodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNodeNotFound(
            NodeNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(CODE_NODE_NOT_FOUND, ex.getMessage(), request.getRequestURI()));
    }

    // ========== 400 Bad Request (parsing errors) ==========

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(CODE_INVALID_REQUEST, "Valor inválido: " + ex.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointer(
            NullPointerException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(CODE_MISSING_REQUIRED_VALUE, "Valor obrigatório não fornecido",
                        request.getRequestURI()));
    }
}
