package br.com.twoapprovalcontentbackend.entrypoint.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SendEvaluationRequest(

        @NotNull(message = "Chave (identificador) do avaliador não pode ser nulo.")
        String evaluationKey,

        @NotNull(message = "Identificador do conteúdo não pode ser nulo.")
        String contentId,

        @NotNull(message = "Status final do conteúdo não pode ser nulo.")
        String status,

        @Size(min = 100, message = "Justificativa deve ter no minimo 100 caracteres.")
        @NotNull(message = "Justificativa não pode ser nulo.")
        String justification
) {}
