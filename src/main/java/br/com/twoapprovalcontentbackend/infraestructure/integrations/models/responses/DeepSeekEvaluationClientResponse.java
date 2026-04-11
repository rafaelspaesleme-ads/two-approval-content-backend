package br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses;

import br.com.twoapprovalcontentbackend.infraestructure.enums.DeepSeekModelEnum;

public record DeepSeekEvaluationClientResponse(
        String id,
        String object,
        Long created,
        DeepSeekModelEnum model,
        DeepSeekEvaluationClientChoicesResponse choice,
        DeepSeekEvaluationClientUsageResponse usage,
        DeepSeekEvaluationClientRateLimitResponse rateLimit
) {
}
