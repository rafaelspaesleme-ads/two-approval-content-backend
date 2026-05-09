package br.com.twoapprovalcontentbackend.application.mappers.authors;

import br.com.twoapprovalcontentbackend.application.mappers.AbstractFunctionsMapper;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.EvaluateContentResponse;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;

public class FindStatusEvaluationMapper extends AbstractFunctionsMapper<String, ContentDocument, EvaluateContentResponse> {
    public FindStatusEvaluationMapper(String contentId) {
        super.setInput(contentId);
    }

    @Override
    public EvaluateContentResponse getBuild() {
        return new EvaluateContentResponse(
                this.getOutput().getId(),
                this.getOutput().getStatus().name(),
                this.getOutput().getStatus().getDescription(),
                this.getOutput().getJustificationFailSendNotifiction()
        );
    }
}
