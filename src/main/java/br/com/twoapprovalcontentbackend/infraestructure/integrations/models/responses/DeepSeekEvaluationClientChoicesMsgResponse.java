package br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses;

public record DeepSeekEvaluationClientChoicesMsgResponse(
        String role,
        String content
) {}
