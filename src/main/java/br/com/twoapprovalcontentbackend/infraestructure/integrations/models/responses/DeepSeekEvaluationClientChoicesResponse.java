package br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses;

public record DeepSeekEvaluationClientChoicesResponse(
        DeepSeekEvaluationClientChoicesMsgResponse message,
        String finishReason
) {}
