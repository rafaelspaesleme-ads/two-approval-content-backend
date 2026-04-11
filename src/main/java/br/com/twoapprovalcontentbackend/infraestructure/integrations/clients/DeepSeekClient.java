package br.com.twoapprovalcontentbackend.infraestructure.integrations.clients;

import br.com.twoapprovalcontentbackend.infraestructure.enums.DeepSeekModelEnum;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses.DeepSeekEvaluationClientResponse;

public interface DeepSeekClient {
    DeepSeekEvaluationClientResponse evaluationContent(String content, DeepSeekModelEnum model);
}
