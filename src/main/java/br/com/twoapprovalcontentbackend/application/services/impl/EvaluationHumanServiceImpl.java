package br.com.twoapprovalcontentbackend.application.services.impl;

import br.com.twoapprovalcontentbackend.application.mappers.evaluations.InfosEvaluationMapper;
import br.com.twoapprovalcontentbackend.application.services.EvaluationHumanService;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.InfosEvaluationResponse;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.ContentGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluationHumanServiceImpl implements EvaluationHumanService {

    private final ContentGateway gateway;

    @Override
    public List<InfosEvaluationResponse> search(String evaluationKey) {

        InfosEvaluationMapper mapper = new InfosEvaluationMapper(evaluationKey);

        mapper.findContents(this.gateway::searchContent);

        mapper.setOutputList(this.gateway::searchAiResponse);

        return mapper.getBuildList();
    }
}
