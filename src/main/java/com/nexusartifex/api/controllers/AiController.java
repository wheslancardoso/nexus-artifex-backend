package com.nexusartifex.api.controllers;

import com.nexusartifex.ai.service.AiEvolutionService;
import com.nexusartifex.api.dto.ai.AiEvolutionRequest;
import com.nexusartifex.api.dto.ai.AiEvolutionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller temporário para testes de evolução via IA.
 */
@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiEvolutionService aiEvolutionService;

    public AiController(AiEvolutionService aiEvolutionService) {
        this.aiEvolutionService = aiEvolutionService;
    }

    /**
     * Evolui um node usando IA com técnica SCAMPER.
     * POST /api/v1/ai/evolve
     */
    @PostMapping("/evolve")
    public ResponseEntity<AiEvolutionResponse> evolve(@RequestBody AiEvolutionRequest request) {
        AiEvolutionResponse response = aiEvolutionService.evolve(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Retorna status da engine de IA.
     * GET /api/v1/ai/status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        return ResponseEntity.ok(Map.of(
                "enabled", aiEvolutionService.isAiEnabled(),
                "available", aiEvolutionService.isEngineAvailable(),
                "provider", aiEvolutionService.getProviderName()));
    }
}
