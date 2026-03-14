package br.com.twoapprovalcontentbackend.infraestructure.exceptions.model;

public record ApiErrorMessageResponse(
        String field,
        String message
) {
    public ApiErrorMessageResponse(String message) {
        this(null, message);
    }
}
