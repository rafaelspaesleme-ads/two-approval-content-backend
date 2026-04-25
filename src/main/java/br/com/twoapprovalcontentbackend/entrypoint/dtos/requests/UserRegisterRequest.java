package br.com.twoapprovalcontentbackend.entrypoint.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record UserRegisterRequest(
        @NotNull(message = "Nome completo do avaliador não pode ser nulo.")
        String fullname,

        @NotNull(message = "E-mail do avaliador não pode ser nulo.")
        String email,

        @NotNull(message = "Nicho do avaliador não pode ser nulo.")
        String niche,

        String comments
) {}
