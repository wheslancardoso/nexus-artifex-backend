package com.nexusartifex.api.dto.response;

import java.time.Instant;

/**
 * Response DTO padronizado para erros da API.
 * 
 * @param code      Código de erro estável (ex: "PROJECT_NOT_FOUND")
 * @param message   Mensagem descritiva do erro
 * @param timestamp Timestamp em UTC (ISO 8601)
 * @param path      Path da requisição que gerou o erro
 */
public record ErrorResponse(
                String code,
                String message,
                Instant timestamp,
                String path) {

        /**
         * Factory method para criar ErrorResponse com timestamp atual.
         */
        public static ErrorResponse of(String code, String message, String path) {
                return new ErrorResponse(code, message, Instant.now(), path);
        }
}
