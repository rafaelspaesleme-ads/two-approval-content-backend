package br.com.twoapprovalcontentbackend.entrypoint.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "Chave do avaliador não pode ser nulo.")
        String evaluationKey,
        @NotNull(message = "SecretID não pode ser nulo.")
        String secretId
) {}
