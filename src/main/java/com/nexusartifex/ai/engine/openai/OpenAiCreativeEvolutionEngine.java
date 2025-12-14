package com.nexusartifex.ai.engine.openai;

import com.nexusartifex.ai.engine.CreativeEvolutionEngine;
import com.nexusartifex.ai.engine.EvolutionContext;
import com.nexusartifex.ai.engine.EvolutionResult;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação da engine de evolução criativa usando OpenAI via LangChain4j.
 */
@Component
@Primary
@ConditionalOnProperty(name = "features.ai.enabled", havingValue = "true")
public class OpenAiCreativeEvolutionEngine implements CreativeEvolutionEngine {

    private static final Logger log = LoggerFactory.getLogger(OpenAiCreativeEvolutionEngine.class);

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
                    .temperature(0.8)
                    .maxTokens(1024)
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

        String prompt = buildPrompt(context);

        try {
            String response = chatModel.generate(prompt);
            log.debug("Resposta OpenAI: {}", response);

            List<EvolutionResult> results = parseResponse(response, context);
            log.info("OpenAI retornou {} resultados", results.size());
            return results;

        } catch (Exception e) {
            log.error("Erro ao chamar OpenAI: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * Constrói o prompt para a OpenAI baseado no contexto.
     */
    private String buildPrompt(EvolutionContext context) {
        String techniqueDescription = getTechniqueDescription(context.technique().name());

        return String.format("""
                Você é um assistente criativo especializado em ideação usando a metodologia SCAMPER.

                Técnica atual: %s
                Descrição: %s

                Conceito original:
                - Label: %s
                - Summary: %s

                Gere exatamente %d variação(ões) criativa(s) aplicando a técnica %s.

                Para cada variação, responda EXATAMENTE neste formato (uma variação por linha):
                LABEL: [novo label conciso] | SUMMARY: [resumo explicando a transformação]

                Seja criativo, ousado e inovador. Fuja do óbvio!
                """,
                context.technique().name(),
                techniqueDescription,
                context.label(),
                context.summary() != null ? context.summary() : "(sem descrição)",
                context.count(),
                context.technique().name());
    }

    /**
     * Retorna descrição da técnica SCAMPER.
     */
    private String getTechniqueDescription(String technique) {
        return switch (technique) {
            case "SUBSTITUTE" -> "O que pode ser substituído? Troque componentes, materiais, processos.";
            case "COMBINE" -> "O que pode ser combinado? Misture ideias, funções, conceitos.";
            case "ADAPT" -> "O que pode ser adaptado de outro contexto? Inspire-se em outras áreas.";
            case "MODIFY" -> "O que pode ser modificado, aumentado ou diminuído?";
            case "PUT_TO_ANOTHER_USE" -> "Onde mais isso poderia ser usado? Novos contextos e aplicações.";
            case "ELIMINATE" -> "O que pode ser eliminado ou simplificado?";
            case "REVERSE" -> "O que acontece se invertermos? Reorganize, mude a ordem.";
            default -> "Aplique transformação criativa.";
        };
    }

    /**
     * Parseia a resposta da OpenAI em EvolutionResult.
     */
    private List<EvolutionResult> parseResponse(String response, EvolutionContext context) {
        List<EvolutionResult> results = new ArrayList<>();

        String[] lines = response.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.contains("LABEL:") && line.contains("SUMMARY:")) {
                try {
                    int labelStart = line.indexOf("LABEL:") + 6;
                    int labelEnd = line.indexOf("|");
                    int summaryStart = line.indexOf("SUMMARY:") + 8;

                    String label = line.substring(labelStart, labelEnd).trim();
                    String summary = line.substring(summaryStart).trim();

                    results.add(new EvolutionResult(label, summary));
                } catch (Exception e) {
                    log.warn("Erro ao parsear linha: {}", line);
                }
            }
        }

        // Fallback se não conseguiu parsear
        if (results.isEmpty() && !response.isBlank()) {
            results.add(new EvolutionResult(
                    "[" + context.technique() + "] " + context.label(),
                    response.substring(0, Math.min(200, response.length()))));
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
