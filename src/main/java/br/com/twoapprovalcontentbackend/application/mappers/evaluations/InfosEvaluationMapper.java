package br.com.twoapprovalcontentbackend.application.mappers.evaluations;

import br.com.twoapprovalcontentbackend.application.mappers.AbstractFunctionsMapper;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.InfosEvaluationResponse;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessConflictException;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.AIResponseDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InfosEvaluationMapper extends AbstractFunctionsMapper<String, AIResponseDocument, InfosEvaluationResponse> {

    private final List<ContentDocument> lsContent = new ArrayList<>();

    public InfosEvaluationMapper(String evaluationKey) {
        super.setInput(evaluationKey);
    }

    public void findContents(Function<String, List<ContentDocument>> result) {
        List<ContentDocument> documentList = result.apply(super.getInput());
        if (!CollectionUtils.isEmpty(documentList)) {
            lsContent.addAll(documentList);
        }
    }

    @Override
    public List<InfosEvaluationResponse> getBuildList() {

        List<InfosEvaluationResponse> response = new ArrayList<>();

        List<AIResponseDocument> lsAiResponse = new ArrayList<>(super.getOutputList());

        lsContent.forEach(content -> {

            InfosEvaluationResponse.AiEvaluation aiEvaluation = lsAiResponse.stream()
                    .filter(aiResponse -> content.getId().equals(aiResponse.getContentId()))
                    .findFirst()
                    .map(aiResponse -> new InfosEvaluationResponse.AiEvaluation(
                            aiResponse.getId(),
                            aiResponse.getExternalIdAI(),
                            aiResponse.getStatus().name(),
                            aiResponse.getJustification(),
                            aiResponse.getCreatedAt().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                            aiResponse.getActive()
                    ))
                            .orElse(null);

            response.add(new InfosEvaluationResponse(
                    super.getInput(),
                    new InfosEvaluationResponse.Content(
                            content.getId(),
                            content.getTitle(),
                            content.getContent(),
                            content.getNiche().name(),
                            content.getStatus().name(),
                            content.getCreatedAt().atOffset(ZoneOffset.UTC).toLocalDateTime(),
                            content.getActive()
                    ),
                    aiEvaluation
            ));
        });

        if (response.stream().anyMatch(infos -> ObjectUtils.isNotEmpty(infos.content()) && ObjectUtils.isEmpty(infos.aiEvaluation()))) {
            throw new BusinessConflictException("Não foi possivel obter avaliação de IA para conteúdo encontrado.");
        }

        return response;
    }
}
