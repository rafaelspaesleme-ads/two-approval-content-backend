package br.com.twoapprovalcontentbackend.entrypoint.controllers.impl;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Slf4j
public abstract class ResponseControllerImpl {

    protected <T> ResponseEntity<ApiResponse<T>> setCreate(T data, String title, List<String> lsMessage, HttpServletRequest request) {
        return this.setCreate(data, title, lsMessage, request, null);
    }
    protected <T> ResponseEntity<ApiResponse<T>> setCreate(T data, String title, List<String> lsMessage, HttpServletRequest request, Object metadata) {

        HttpStatus status = HttpStatus.CREATED;

        return buildGlobalResponse(data, title, lsMessage, request, metadata, status);
    }

    protected <T> ResponseEntity<ApiResponse<T>> setOk(T data, String title, List<String> lsMessage, HttpServletRequest request) {
        return this.setOk(data, title, lsMessage, request, null);
    }

    protected <T> ResponseEntity<ApiResponse<T>> setVoidOk(String title, List<String> lsMessage, HttpServletRequest request) {
        return this.setOk(null, title, lsMessage, request, null);
    }

    protected <T> ResponseEntity<ApiResponse<T>> setOk(T data, String title, List<String> lsMessage, HttpServletRequest request, Object metadata) {

        HttpStatus status = HttpStatus.OK;

        return buildGlobalResponse(data, title, lsMessage, request, metadata, status);
    }

    private static <T> ResponseEntity<ApiResponse<T>> buildGlobalResponse(T data, String title, List<String> lsMessage, HttpServletRequest request, Object metadata, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>(
                UUID.randomUUID().toString(),
                data,
                title,
                lsMessage,
                request.getRequestURI(),
                LocalDateTime.now(),
                metadata
        );

        log.info("[%s]::[%s]::[%s]::[%s]".formatted(response.timestamp(), response.responseId(), status.name(), response.toString()));

        return ResponseEntity.status(status).body(response);
    }

}
