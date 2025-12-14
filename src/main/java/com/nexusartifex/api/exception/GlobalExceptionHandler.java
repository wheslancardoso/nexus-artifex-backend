package com.nexusartifex.api.exception;

import com.nexusartifex.shared.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Handler global para exceções de domínio.
 * Mapeia exceções para respostas HTTP apropriadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ========== 400 Bad Request ==========

    @ExceptionHandler(InvalidProjectException.class)
    public ResponseEntity<Map<String, String>> handleInvalidProject(InvalidProjectException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(InvalidNodeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidNode(InvalidNodeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(InvalidEvolutionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEvolution(InvalidEvolutionException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(InvalidGraphOperationException.class)
    public ResponseEntity<Map<String, String>> handleInvalidGraphOperation(InvalidGraphOperationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    // ========== 404 Not Found ==========

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProjectNotFound(ProjectNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(NodeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNodeNotFound(NodeNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }
}
