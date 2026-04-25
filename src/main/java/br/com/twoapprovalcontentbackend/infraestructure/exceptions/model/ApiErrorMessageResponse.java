package br.com.twoapprovalcontentbackend.infraestructure.exceptions.model;

import java.util.Optional;

public record ApiErrorMessageResponse(
        String field,
        String message
) {
    public ApiErrorMessageResponse(String message) {
        this(null, message);
    }

    @Override
    public String toString() {
        return "{" +
                "\"field\":" + Optional.ofNullable(field).map("\"%s\""::formatted).orElse(null) + "," +
                "\"message\":" + Optional.ofNullable(message).map("\"%s\""::formatted).orElse(null) +
                "}";
    }
}
