package br.com.twoapprovalcontentbackend.entrypoint.dtos.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record EvaluateContentRequest(
        @NotNull(message = "Nome completo do criador de conteúdo não pode ser nulo.")
        String fullname,
        @NotNull(message = "Titulo do conteúdo não pode ser nulo.")
        String title,
        @NotNull(message = "Conteúdo não pode ser nulo.")
        String content,
        @NotNull(message = "E-mail do criador de conteúdo não pode ser nulo.")
        String email,
        @NotNull(message = "Nicho do conteúdo não pode ser nulo.")
        String niche,
        @Nullable
        Notification notification
) {
    public record Notification(
            String url,
            String httpMethod
    ) {}
}
