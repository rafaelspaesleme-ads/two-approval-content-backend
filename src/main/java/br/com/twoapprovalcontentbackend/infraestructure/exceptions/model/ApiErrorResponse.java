package br.com.twoapprovalcontentbackend.infraestructure.exceptions.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record ApiErrorResponse(
        @NotNull
        String errorId,
        String title,
        List<ApiErrorMessageResponse> message,
        String local,
        LocalDateTime timestamp,
        Object metadata
) {

    @Override
    public String toString() {
        return "{" +
                "\"errorId\":\"" + errorId + "\"," +
                "\"title\":\"'" + title + "\"," +
                "\"message\":\"" + message + "\"," +
                "\"local\":\"'" + local + "\"," +
                "\"timestamp\":\"" + timestamp.toString() + "\"," +
                "\"metadata\":\"" + Optional.ofNullable(metadata).orElse("{}") + "\"" +
                "}";
    }
}
