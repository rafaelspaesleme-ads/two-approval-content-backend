package br.com.twoapprovalcontentbackend.application.services.impl;

import br.com.twoapprovalcontentbackend.application.mappers.authors.EvaluateContentMapper;
import br.com.twoapprovalcontentbackend.application.mappers.authors.FindStatusEvaluationMapper;
import br.com.twoapprovalcontentbackend.application.services.AuthorService;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.EvaluateContentRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.EvaluateContentResponse;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.AIGateway;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.ContentGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final ContentGateway contentGateway;

    private final AIGateway aiGateway;

    @Override
    public EvaluateContentResponse evaluate(EvaluateContentRequest request) {

        EvaluateContentMapper mapper = new EvaluateContentMapper(request);

        mapper.findUserEvaluation(this.contentGateway::getUserEvaluation);

        mapper.setOutput(this.contentGateway::save);

        mapper.setEvaluationContent(this.aiGateway::evaluationContent);

        return mapper.getBuild();
    }

    @Override
    public EvaluateContentResponse findStatus(String contentId) {

        FindStatusEvaluationMapper mapper = new FindStatusEvaluationMapper(contentId);

        mapper.setOutput(this.contentGateway::findAndUpdateFlagFindStatus);

        return mapper.getBuild();
    }
}
