package br.com.twoapprovalcontentbackend.entrypoint.dtos.responses;

import java.time.LocalDateTime;

public record LoginResponse(
        String evaluationKey,
        String token,
        LocalDateTime initialLoginAt,
        LocalDateTime expiresLoginAt
) {}
