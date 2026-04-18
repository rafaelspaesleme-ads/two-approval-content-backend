package br.com.twoapprovalcontentbackend.infraestructure.gateways;

import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;

public interface EvaluatorGateway {
    UserEvaluatorDocument register(UserEvaluatorDocument document);
}
