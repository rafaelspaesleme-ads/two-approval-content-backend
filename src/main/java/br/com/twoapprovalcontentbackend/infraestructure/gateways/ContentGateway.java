package br.com.twoapprovalcontentbackend.infraestructure.gateways;

import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;

public interface ContentGateway {
    UserEvaluatorDocument getUserEvaluation(NichesEnum niche);

    ContentDocument save(ContentDocument document);
}
