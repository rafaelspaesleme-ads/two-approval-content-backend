package br.com.twoapprovalcontentbackend.infraestructure.gateways;

import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.AIResponseDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;

import java.util.List;

public interface ContentGateway {
    UserEvaluatorDocument getUserEvaluation(NichesEnum niche);

    ContentDocument save(ContentDocument document);

    ContentDocument findAndUpdateFlagFindStatus(String contentId);

    List<ContentDocument> searchContent(String evaluationKey);

    List<AIResponseDocument> searchAiResponse(String evaluationKey);
}
