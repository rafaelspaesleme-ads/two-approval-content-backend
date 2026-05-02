package br.com.twoapprovalcontentbackend.application.services.impl;

import br.com.twoapprovalcontentbackend.application.mappers.authors.EvaluateContentMapper;
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

        EvaluateContentMapper builder = new EvaluateContentMapper(request);

        builder.findUserEvaluation(this.contentGateway::getUserEvaluation);

        builder.setOutput(this.contentGateway::save);

        builder.setEvaluationContent(this.aiGateway::evaluationContent);

        return builder.getBuild();
    }
}
