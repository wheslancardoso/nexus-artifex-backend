package com.nexusartifex.ai.engine.openai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexusartifex.ai.engine.CreativeEvolutionEngine;
import com.nexusartifex.ai.engine.EvolutionContext;
import com.nexusartifex.ai.engine.EvolutionResult;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementação da engine de evolução criativa usando OpenAI via LangChain4j.
 */
@Component
@Primary
@ConditionalOnProperty(name = "features.ai.enabled", havingValue = "true")
public class OpenAiCreativeEvolutionEngine implements CreativeEvolutionEngine {

    private static final Logger log = LoggerFactory.getLogger(OpenAiCreativeEvolutionEngine.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * System prompt fixo para consistência e qualidade.
     */
    private static final String SYSTEM_PROMPT = """
            You are Nexus Artifex, a creative evolution engine.

            Your role is to generate concise, high-quality creative mutations of an idea using the SCAMPER technique.

            Rules:
            - Be concrete and specific, not generic.
            - Avoid buzzwords and vague phrases.
            - Focus on practical, creative evolution.
            - Do not repeat the original idea verbatim.
            - Keep responses short and structured.
            """;

    private final ChatLanguageModel chatModel;
    private final boolean available;

    public OpenAiCreativeEvolutionEngine(
            @Value("${openai.api.key:}") String apiKey,
            @Value("${openai.model:gpt-4o-mini}") String model) {

        if (apiKey == null || apiKey.isBlank()) {
            log.warn("OpenAI API key não configurada - engine indisponível");
            this.chatModel = null;
            this.available = false;
        } else {
            log.info("Inicializando OpenAI engine com modelo: {}", model);
            this.chatModel = OpenAiChatModel.builder()
                    .apiKey(apiKey)
                    .modelName(model)
                    .temperature(0.7) // Balanceado: criatividade + consistência
                    .maxTokens(300) // Reduzido para respostas concisas
                    .build();
            this.available = true;
        }
    }

    @Override
    public List<EvolutionResult> evolve(EvolutionContext context) {
        if (!available || chatModel == null) {
            log.warn("OpenAI engine indisponível - retornando lista vazia");
            return List.of();
        }

        log.info("Chamando OpenAI para evoluir '{}' com técnica {}",
                context.label(), context.technique());

        String userPrompt = buildUserPrompt(context);

        try {
            Response<AiMessage> response = chatModel.generate(
                    SystemMessage.from(SYSTEM_PROMPT),
                    UserMessage.from(userPrompt));

            String content = response.content().text();
            log.debug("Resposta OpenAI: {}", content);

            List<EvolutionResult> results = parseJsonResponse(content);
            log.info("OpenAI retornou {} resultados", results.size());
            return results;

        } catch (Exception e) {
            log.error("Erro ao chamar OpenAI: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * Constrói o user prompt estruturado.
     */
    private String buildUserPrompt(EvolutionContext context) {
        return String.format("""
                Original idea:
                Title: "%s"
                Description: "%s"

                SCAMPER technique: %s

                Task:
                Generate %d distinct creative evolutions of the original idea using the SCAMPER technique.

                Output rules:
                - Return ONLY valid JSON.
                - No explanations.
                - No markdown.
                - No extra text.

                JSON format:
                [
                  {
                    "label": "<short, specific title>",
                    "summary": "<1–2 sentences, concrete evolution>"
                  }
                ]
                """,
                context.label(),
                context.summary() != null ? context.summary() : "(sem descrição)",
                context.technique().name(),
                context.count());
    }

    /**
     * Parseia resposta JSON da OpenAI.
     */
    private List<EvolutionResult> parseJsonResponse(String response) {
        List<EvolutionResult> results = new ArrayList<>();

        try {
            // Limpar possíveis marcadores de código
            String cleanJson = response.trim();
            if (cleanJson.startsWith("```json")) {
                cleanJson = cleanJson.substring(7);
            }
            if (cleanJson.startsWith("```")) {
                cleanJson = cleanJson.substring(3);
            }
            if (cleanJson.endsWith("```")) {
                cleanJson = cleanJson.substring(0, cleanJson.length() - 3);
            }
            cleanJson = cleanJson.trim();

            // Parse JSON array
            List<Map<String, String>> parsed = objectMapper.readValue(
                    cleanJson,
                    new TypeReference<List<Map<String, String>>>() {
                    });

            for (Map<String, String> item : parsed) {
                String label = item.get("label");
                String summary = item.get("summary");
                if (label != null && !label.isBlank()) {
                    results.add(new EvolutionResult(label, summary != null ? summary : ""));
                }
            }

        } catch (Exception e) {
            log.warn("Erro ao parsear JSON: {} - Resposta: {}", e.getMessage(), response);
        }

        return results;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getProviderName() {
        return "OpenAI";
    }
}
