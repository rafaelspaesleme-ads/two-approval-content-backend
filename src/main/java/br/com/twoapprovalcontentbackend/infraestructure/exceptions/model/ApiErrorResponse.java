package br.com.twoapprovalcontentbackend.infraestructure.exceptions.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        String messageJson = Optional.ofNullable(message)
                .filter(lsMsg -> !CollectionUtils.isEmpty(lsMsg))
                .map(lsMsg -> {
                    String lsMsgJson = lsMsg.stream().map(ApiErrorMessageResponse::toString).collect(Collectors.joining(","));
                    return "[%s]".formatted(String.join(",", lsMsgJson));
                }).orElse("[]");



        return "{" +
                "\"errorId\":\"" + errorId + "\"," +
                "\"title\":\"" + title + "\"," +
                "\"message\":" + messageJson + "," +
                "\"local\":\"" + local + "\"," +
                "\"timestamp\":\"" + timestamp.toString() + "\"," +
                "\"metadata\":" + Optional.ofNullable(metadata).map("\"%s\""::formatted).orElse("{}") +
                "}";
    }
}
