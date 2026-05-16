package br.com.twoapprovalcontentbackend.entrypoint.dtos.responses;

import java.time.LocalDateTime;

public record InfosEvaluationResponse(
        String evaluationId,
        Content content,
        AiEvaluation aiEvaluation
) {

    public record Content(
            String id,
            String title,
            String content,
            String niche,
            String currentStatus,
            LocalDateTime createdAt,
            Boolean active
    ) {}

    public record AiEvaluation(
            String id,
            String evaluationAiId,
            String currentStatus,
            String justification,
            LocalDateTime createdAt,
            Boolean active
    ) {}

}
