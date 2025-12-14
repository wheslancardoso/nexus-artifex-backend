package com.nexusartifex.api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Interceptor de rate limiting simples (60 req/min por IP).
 * Exclui endpoints SSE e health do limite.
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS_PER_MINUTE = 60;
    private static final long WINDOW_MS = 60_000; // 1 minuto

    /**
     * Armazena contagem de requests por IP.
     */
    private final Map<String, RateLimitEntry> requestCounts = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        String path = request.getRequestURI();

        // Excluir endpoints SSE e health do rate limit
        if (isExcludedPath(path)) {
            return true;
        }

        String clientIp = getClientIp(request);
        RateLimitEntry entry = requestCounts.computeIfAbsent(clientIp, k -> new RateLimitEntry());

        // Verificar se janela expirou
        long now = System.currentTimeMillis();
        if (now - entry.windowStart > WINDOW_MS) {
            entry.reset(now);
        }

        // Verificar limite
        if (entry.count.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"code\":\"RATE_LIMIT_EXCEEDED\",\"message\":\"Limite de requisições excedido. Tente novamente em alguns segundos.\"}");
            return false;
        }

        return true;
    }

    /**
     * Verifica se o path deve ser excluído do rate limit.
     */
    private boolean isExcludedPath(String path) {
        return path.contains("/stream") ||
                path.contains("/health") ||
                path.contains("/events/test");
    }

    /**
     * Obtém IP do cliente considerando proxies.
     */
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    /**
     * Entrada de rate limit por IP.
     */
    private static class RateLimitEntry {
        volatile long windowStart = System.currentTimeMillis();
        final AtomicInteger count = new AtomicInteger(0);

        void reset(long newStart) {
            windowStart = newStart;
            count.set(0);
        }
    }
}
