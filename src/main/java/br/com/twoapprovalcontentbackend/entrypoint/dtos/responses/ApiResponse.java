package br.com.twoapprovalcontentbackend.entrypoint.dtos.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public record ApiResponse<T>(
        @NotNull
        String responseId,
        T data,
        String title,
        List<String> lsMessage,
        String local,
        LocalDateTime timestamp,
        Object metadata
) {

    @Override
    public String toString() {

        String lsMsg = CollectionUtils.isEmpty(lsMessage) ? "[]" : "[%s]".formatted(lsMessage.stream().map("\"%s\""::formatted).collect(Collectors.joining(",")));

        ObjectMapper mapper = new ObjectMapper();

        long size = 0;
        long count = 0;

        if (Objects.nonNull(data) && ObjectUtils.isArray(data)) {
            Object[] array = ObjectUtils.toObjectArray(data);
            count = array.length;
            try {
                size = mapper.writeValueAsBytes(data).length;
            } catch (JsonProcessingException e) {
                log.error(e.getOriginalMessage());
            }
        }

        if (Objects.nonNull(data) && !ObjectUtils.isArray(data)) {
            count = 1;
            try {
                size = mapper.writeValueAsBytes(data).length;
            } catch (JsonProcessingException e) {
                log.error(e.getOriginalMessage());
            }
        }

        return "{" +
                "\"responseId\":\"" + responseId + "\"," +
                "\"title\":\"'" + title + "\"," +
                "\"lsMessage\":\"" + lsMsg + "\"," +
                "\"countData\":\"" + count + "\"," +
                "\"sizeData\":\"" + size + "B\"," +
                "\"local\":\"'" + local + "\"," +
                "\"timestamp\":\"" + timestamp.toString() + "\"," +
                "\"metadata\":\"" + Optional.ofNullable(metadata).map(Object::toString).orElse("{}") + "\"" +
                "}";
    }
}
