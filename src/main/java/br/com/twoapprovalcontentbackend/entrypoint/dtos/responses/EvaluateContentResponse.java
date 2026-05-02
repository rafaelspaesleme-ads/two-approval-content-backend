package br.com.twoapprovalcontentbackend.entrypoint.dtos.responses;

public record EvaluateContentResponse(
        String contentId,
        String status,
        String statusDescription
) {}
