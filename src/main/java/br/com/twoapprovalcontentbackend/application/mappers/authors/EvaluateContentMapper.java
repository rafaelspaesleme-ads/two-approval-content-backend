package br.com.twoapprovalcontentbackend.application.mappers.authors;

import br.com.twoapprovalcontentbackend.application.mappers.AbstractFunctionsMapper;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.EvaluateContentRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.EvaluateContentResponse;
import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import br.com.twoapprovalcontentbackend.infraestructure.enums.StatusEvaluationEnum;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.references.WebhookDataReference;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class EvaluateContentMapper extends AbstractFunctionsMapper<ContentDocument, ContentDocument, EvaluateContentResponse> {

    private final EvaluateContentRequest request;

    public EvaluateContentMapper(EvaluateContentRequest request) {
        this.request = request;
    }

    public void findUserEvaluation(Function<NichesEnum, UserEvaluatorDocument> result) {
        UserEvaluatorDocument userEvaluator = result.apply(NichesEnum.findByAny(this.request.niche()));

        WebhookDataReference webhook = Optional.ofNullable(this.request.notification())
                .map(notification -> WebhookDataReference.builder()
                        .url(notification.url())
                        .method(notification.httpMethod())
                        .build())
                .orElse(null);

        super.setInput(ContentDocument.builder()
                        .fullname(this.request.fullname())
                        .title(this.request.title())
                        .status(StatusEvaluationEnum.IN_REVIEW)
                        .content(this.request.content())
                        .evaluationKey(userEvaluator.getId())
                        .creatorEmail(this.request.email())
                        .niche(userEvaluator.getNiche())
                        .webhook(webhook)
                .build());
    }

    public void setEvaluationContent(Consumer<ContentDocument> result) {
        result.accept(super.getOutput());
    }

    @Override
    public EvaluateContentResponse getBuild() {
        return new EvaluateContentResponse(
                super.getOutput().getId(),
                super.getOutput().getStatus().name(),
                super.getOutput().getStatus().getDescription()
        );
    }
}
