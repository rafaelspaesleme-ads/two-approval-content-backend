package br.com.twoapprovalcontentbackend.application.services.impl;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.EvaluateContentRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.EvaluateContentResponse;
import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessNotFoundException;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.AIGateway;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.ContentGateway;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static mocks.BuildMocks.buildContentDocument;
import static mocks.BuildMocks.buildEvaluateContentRequest;
import static mocks.BuildMocks.buildUserEvaluatorDocument;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private ContentGateway contentGateway;

    @Mock
    private AIGateway aiGateway;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    void evaluate_success() {

        EvaluateContentRequest request = buildEvaluateContentRequest();

        UserEvaluatorDocument evaluatorDocument = buildUserEvaluatorDocument();

        ContentDocument contentSaved = buildContentDocument(request, evaluatorDocument);

        when(contentGateway.getUserEvaluation(Mockito.any(NichesEnum.class)))
                .thenReturn(evaluatorDocument);


        when(contentGateway.save(Mockito.any(ContentDocument.class)))
                .thenReturn(contentSaved);

        doNothing().when(aiGateway).evaluationContent(contentSaved);


        EvaluateContentResponse response = service.evaluate(request);

        Assertions.assertEquals(contentSaved.getId(), response.contentId());
        Assertions.assertEquals(contentSaved.getStatus().name(), response.status());
        Assertions.assertEquals(contentSaved.getStatus().getDescription(), response.statusDescription());
        Assertions.assertNull(response.justificationFailSendNotifiction());

    }

    @Test
    void evaluate_fail_not_found_evaluation() {

        String msg = "Sem avaliador humano para o nicho escolhido.";

        EvaluateContentRequest request = buildEvaluateContentRequest();

        when(contentGateway.getUserEvaluation(Mockito.any(NichesEnum.class)))
                .thenThrow(new BusinessNotFoundException(msg));

        Assertions.assertThrows(BusinessNotFoundException.class, () -> service.evaluate(request), msg);

    }

    @Test
    void findStatus_success() {

        ContentDocument contentFound = buildContentDocument(buildEvaluateContentRequest(), buildUserEvaluatorDocument());

        when(contentGateway.findAndUpdateFlagFindStatus(Mockito.anyString()))
                .thenReturn(contentFound);

        EvaluateContentResponse response = service.findStatus(contentFound.getId());

        Assertions.assertEquals(contentFound.getId(), response.contentId());
        Assertions.assertEquals(contentFound.getStatus().name(), response.status());
        Assertions.assertEquals(contentFound.getStatus().getDescription(), response.statusDescription());

    }

    @Test
    void findStatus_fail_not_found_content() {

        String msg = "Conteúdo não encontrado";

        when(contentGateway.findAndUpdateFlagFindStatus(Mockito.anyString()))
                .thenThrow(new BusinessNotFoundException(msg));

        Assertions.assertThrows(BusinessNotFoundException.class, () -> service.findStatus("123"), msg);

    }

}