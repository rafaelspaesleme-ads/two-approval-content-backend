package br.com.twoapprovalcontentbackend.entrypoint.dtos.responses;

public record UserRegisterResponse(
        String clientId,
        String secretId
) {}
