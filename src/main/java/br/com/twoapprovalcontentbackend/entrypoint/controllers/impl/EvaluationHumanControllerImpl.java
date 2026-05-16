package br.com.twoapprovalcontentbackend.entrypoint.controllers.impl;

import br.com.twoapprovalcontentbackend.application.services.EvaluationHumanService;
import br.com.twoapprovalcontentbackend.entrypoint.controllers.EvaluationHumanController;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.InfosEvaluationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EvaluationHumanControllerImpl extends ResponseControllerImpl implements EvaluationHumanController {

    private final EvaluationHumanService service;

    @Override
    public ResponseEntity<ApiResponse<List<InfosEvaluationResponse>>> search(String evaluationKey, HttpServletRequest servletRequest) {

        List<InfosEvaluationResponse> response = service.search(evaluationKey);

        return super.setOk(
                response,
                "Consulta de conteúdos para analise.",
                Collections.singletonList("Conteúdos retornados com sucesso para avaliação humana."),
                servletRequest
        );
    }
}
