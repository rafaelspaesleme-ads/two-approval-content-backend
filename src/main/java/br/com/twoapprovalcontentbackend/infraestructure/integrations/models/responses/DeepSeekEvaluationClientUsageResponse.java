package br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses;

public record DeepSeekEvaluationClientUsageResponse(
        Integer promptTokens,
        Integer completionTokens,
        Integer totalTokens
) {}
