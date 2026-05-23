package br.com.twoapprovalcontentbackend.application.mappers.evaluations;

import br.com.twoapprovalcontentbackend.application.mappers.AbstractFunctionsMapper;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.SendEvaluationRequest;
import br.com.twoapprovalcontentbackend.infraestructure.enums.StatusEvaluationEnum;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessConflictException;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SendEvaluationMapper extends AbstractFunctionsMapper<ContentDocument, Void, Void> {

    private final SendEvaluationRequest request;

    public SendEvaluationMapper(SendEvaluationRequest request) {
        this.request = request;
    }

    public void findContentById(Function<String, ContentDocument> result) {
        ContentDocument document = result.apply(this.request.contentId());

        if (!this.request.evaluationKey().equals(document.getEvaluationKey())) {
            throw new BusinessConflictException("A chave informada pelo avaliador não corresponde à chave registrada no conteúdo.");
        }

        document.setStatus(StatusEvaluationEnum.findBy(this.request.status()));

        if (StatusEvaluationEnum.IN_REVIEW.equals(document.getStatus())) {
            throw new BusinessConflictException("É necessário finalizar o status, seja para aprovação ou reprovação.");
        }

        document.setJustificationEvaluation(this.request.justification());

        super.setInput(document);
    }

    public void removeEvaluationAI(BiConsumer<String, String> result) {
        result.accept(this.request.contentId(), this.request.evaluationKey());
    }

}
