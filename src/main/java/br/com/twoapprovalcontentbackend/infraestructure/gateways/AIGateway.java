package br.com.twoapprovalcontentbackend.infraestructure.gateways;

import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;

public interface AIGateway {
    void evaluationContent(ContentDocument document);
}
