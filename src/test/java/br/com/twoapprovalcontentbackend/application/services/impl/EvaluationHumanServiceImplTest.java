package br.com.twoapprovalcontentbackend.application.services.impl;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.SendEvaluationRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.InfosEvaluationResponse;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessConflictException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessNotFoundException;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.ContentGateway;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.AIResponseDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static mocks.BuildMocks.buildAIResponseDocument;
import static mocks.BuildMocks.buildContentDocument;
import static mocks.BuildMocks.buildSendEvaluationRequest;
import static mocks.BuildMocks.buildSendEvaluationRequestInReview;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluationHumanServiceImplTest {

    @Mock
    private ContentGateway gateway;

    @InjectMocks
    private EvaluationHumanServiceImpl service;

    @Test
    void search_success_not_empty() {

        ContentDocument contentDocument = buildContentDocument();

        AIResponseDocument aiResponseDocument = buildAIResponseDocument();

        when(gateway.searchContent(Mockito.anyString()))
                .thenReturn(Collections.singletonList(contentDocument));

        when(gateway.searchAiResponse(Mockito.anyString()))
                .thenReturn(Collections.singletonList(aiResponseDocument));

        List<InfosEvaluationResponse> response = service.search(contentDocument.getEvaluationKey());

        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(response.getFirst().evaluationId(), contentDocument.getEvaluationKey());
        Assertions.assertEquals(response.getFirst().aiEvaluation().id(), aiResponseDocument.getId());
        Assertions.assertEquals(response.getFirst().aiEvaluation().evaluationAiId(), aiResponseDocument.getExternalIdAI());

    }

    @Test
    void search_success_empty() {

        ContentDocument contentDocument = buildContentDocument();

        when(gateway.searchContent(Mockito.anyString()))
                .thenReturn(Collections.emptyList());

        when(gateway.searchAiResponse(Mockito.anyString()))
                .thenReturn(Collections.emptyList());

        List<InfosEvaluationResponse> response = service.search(contentDocument.getEvaluationKey());

        Assertions.assertTrue(response.isEmpty());

    }

    @Test
    void search_fail_builder_response() {

        ContentDocument contentDocument = buildContentDocument();

        AIResponseDocument aiResponseDocument = buildAIResponseDocument();
        aiResponseDocument.setContentId(UUID.randomUUID().toString());

        when(gateway.searchContent(Mockito.anyString()))
                .thenReturn(Collections.singletonList(contentDocument));

        when(gateway.searchAiResponse(Mockito.anyString()))
                .thenReturn(Collections.singletonList(aiResponseDocument));

        Assertions.assertThrows(BusinessConflictException.class, () -> service.search("1010"), "Não foi possivel obter avaliação de IA para conteúdo encontrado.");
    }

    @Test
    void sendEvaluation_success() {

        ContentDocument contentDocument = buildContentDocument();

        when(gateway.findContentById(Mockito.anyString()))
                .thenReturn(contentDocument);

        doNothing().when(gateway).sendEvaluation(contentDocument);

        doNothing().when(gateway).removeEvaluationAI(contentDocument.getId(), contentDocument.getEvaluationKey());

        Assertions.assertAll(() -> service.sendEvaluation(buildSendEvaluationRequest()));

    }

    @Test
    void sendEvaluation_fail_evaluation_key_conflict() {

        SendEvaluationRequest request = buildSendEvaluationRequest();

        ContentDocument contentDocument = buildContentDocument();

        contentDocument.setEvaluationKey(UUID.randomUUID().toString());

        when(gateway.findContentById(Mockito.anyString()))
                .thenReturn(contentDocument);

        Assertions.assertThrows(BusinessConflictException.class, () -> service.sendEvaluation(request), "A chave informada pelo avaliador não corresponde à chave registrada no conteúdo.");

    }

    @Test
    void sendEvaluation_fail_status_conflict() {

        SendEvaluationRequest request = buildSendEvaluationRequestInReview();

        ContentDocument contentDocument = buildContentDocument();

        contentDocument.setEvaluationKey(UUID.randomUUID().toString());

        when(gateway.findContentById(Mockito.anyString()))
                .thenReturn(contentDocument);

        Assertions.assertThrows(BusinessConflictException.class, () -> service.sendEvaluation(request), "É necessário finalizar o status, seja para aprovação ou reprovação.");

    }

    @Test
    void sendEvaluation_fail_content_not_found() {

        String msg = "Conteúdo não encontrado.";

        SendEvaluationRequest request = buildSendEvaluationRequest();

        when(gateway.findContentById(Mockito.anyString()))
                .thenThrow(new BusinessNotFoundException(msg));


        Assertions.assertThrows(BusinessNotFoundException.class, () -> service.sendEvaluation(request), "Conteúdo não encontrado.");

    }

}