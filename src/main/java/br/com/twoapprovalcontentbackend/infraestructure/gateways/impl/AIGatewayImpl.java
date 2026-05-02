package br.com.twoapprovalcontentbackend.infraestructure.gateways.impl;

import br.com.twoapprovalcontentbackend.infraestructure.enums.AIStatusEvaluationEnum;
import br.com.twoapprovalcontentbackend.infraestructure.enums.DeepSeekModelEnum;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.AIGateway;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.clients.DeepSeekClient;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses.DeepSeekEvaluationClientChoicesMsgResponse;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses.DeepSeekEvaluationClientChoicesResponse;
import br.com.twoapprovalcontentbackend.infraestructure.integrations.models.responses.DeepSeekEvaluationClientResponse;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.AIResponseDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories.AIResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
@RequiredArgsConstructor
public class AIGatewayImpl extends PersistGatewayImpl implements AIGateway {

    private final DeepSeekClient client;

    private final AIResponseRepository repository;

    @Async(value = "asyncExecutor")
    @Override
    public void evaluationContent(ContentDocument document) {

        String content = "%s\n%s".formatted(document.getTitle(), document.getContent());

        DeepSeekEvaluationClientResponse responseClient = this.client.evaluationContent(content, DeepSeekModelEnum.DEEPSEEK_REASONER);

        AtomicReference<String> justification = new AtomicReference<>("Sem justificativa.");
        AtomicReference<AIStatusEvaluationEnum> status = new AtomicReference<>(AIStatusEvaluationEnum.NO_ANSWER);

        Optional.ofNullable(responseClient.choice())
                .map(DeepSeekEvaluationClientChoicesResponse::message)
                .map(DeepSeekEvaluationClientChoicesMsgResponse::content)
                .ifPresent(c -> {
                    String statusAI = c.split("::")[0];
                    String justificationAI = c.split("::")[1];

                    status.set(AIStatusEvaluationEnum.findBy(statusAI));
                    justification.set(justificationAI);
                });

        this.repository.save(super.create(AIResponseDocument.builder()
                        .status(status.get())
                        .justification(justification.get())
                        .externalIdAI(responseClient.id())
                        .evaluationKey(document.getEvaluationKey())
                        .contentId(document.getId())
                .build()));

    }
}
