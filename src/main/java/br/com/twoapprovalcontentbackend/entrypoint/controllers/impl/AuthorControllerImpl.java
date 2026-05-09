package br.com.twoapprovalcontentbackend.entrypoint.controllers.impl;

import br.com.twoapprovalcontentbackend.application.services.AuthorService;
import br.com.twoapprovalcontentbackend.entrypoint.controllers.AuthorController;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.EvaluateContentRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.EvaluateContentResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthorControllerImpl extends ResponseControllerImpl implements AuthorController {

    private final AuthorService service;

    @Override
    public ResponseEntity<ApiResponse<EvaluateContentResponse>> evaluate(EvaluateContentRequest request, HttpServletRequest servletRequest) {

        EvaluateContentResponse response = service.evaluate(request);

        return super.setOk(
                response,
                "Conteúdo enviado para analise.",
                Collections.singletonList("Seu conteúdo esta em analise, em breve você será notificado sobre o resultado da avaliação."),
                servletRequest
        );
    }

    @Override
    public ResponseEntity<ApiResponse<EvaluateContentResponse>> findStatus(String contentId, HttpServletRequest servletRequest) {

        EvaluateContentResponse response = service.findStatus(contentId);

        return super.setOk(
                response,
                "Status da analise.",
                Collections.singletonList("Status da analise retornado com sucesso!"),
                servletRequest
        );
    }
}
