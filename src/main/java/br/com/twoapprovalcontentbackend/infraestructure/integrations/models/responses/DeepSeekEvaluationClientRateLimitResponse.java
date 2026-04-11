package br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses;

public record DeepSeekEvaluationClientRateLimitResponse(
        Long requestsLimit,
        Long tokensLimit
) {
}
