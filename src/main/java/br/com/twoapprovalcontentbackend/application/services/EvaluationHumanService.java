package br.com.twoapprovalcontentbackend.application.services;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.InfosEvaluationResponse;

import java.util.List;

public interface EvaluationHumanService {
    List<InfosEvaluationResponse> search(String evaluationKey);
}
