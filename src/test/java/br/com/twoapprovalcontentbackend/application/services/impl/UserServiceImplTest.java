package br.com.twoapprovalcontentbackend.application.services.impl;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.LoginRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.UserRegisterRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.LoginResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.UserRegisterResponse;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessNotFoundException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessUnauthorizedException;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.EvaluatorGateway;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import mocks.BuildMocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private EvaluatorGateway evaluatorGateway;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void register_success() {

        UserRegisterRequest request = BuildMocks.buildUserRegisterRequest();

        UserEvaluatorDocument evaluatorDocument = BuildMocks.buildUserEvaluatorDocument();

        when(evaluatorGateway.register(Mockito.any(UserEvaluatorDocument.class)))
                .thenReturn(evaluatorDocument);

        UserRegisterResponse response = service.register(request);

        Assertions.assertNotNull(response.secretId());
        Assertions.assertTrue(response.secretId().contains("tac_live_v1_"));
        Assertions.assertEquals(evaluatorDocument.getId(), response.evaluationKey());

    }

    @Test
    void register_fail_niche_not_found() {

        UserRegisterRequest request = BuildMocks.buildUserRegisterRequestNotFountNiche();

        Assertions.assertThrows(BusinessNotFoundException.class, () -> service.register(request), "Nicho não encontrado. Entre em contato com nosso suporte técnico.");

    }

    @Test
    void login_success() {

        LoginRequest request = BuildMocks.buildLoginRequest();

        UserEvaluatorDocument evaluatorDocument = BuildMocks.buildUserEvaluatorDocument();

        when(evaluatorGateway.login(Mockito.any(UserEvaluatorDocument.class)))
                .thenReturn(evaluatorDocument);

        LoginResponse response = service.login(request);

        Assertions.assertEquals(evaluatorDocument.getId(), request.evaluationKey());
        Assertions.assertNotNull(response.token());
        Assertions.assertNotNull(response.initialLoginAt());
        Assertions.assertNotNull(response.expiresLoginAt());

    }

    @Test
    void login_fail_evaluation_not_found() {

        String msg = "Usuário avaliador não existe.";

        LoginRequest request = BuildMocks.buildLoginRequest();

        when(evaluatorGateway.login(Mockito.any(UserEvaluatorDocument.class)))
                .thenThrow(new BusinessNotFoundException(msg));

        Assertions.assertThrows(BusinessNotFoundException.class, () -> service.login(request), msg);

    }

    @Test
    void login_fail_unauthorized() {

        String msg = "Não foi possivel realizar o login.";

        LoginRequest request = BuildMocks.buildLoginRequest();

        when(evaluatorGateway.login(Mockito.any(UserEvaluatorDocument.class)))
                .thenThrow(new BusinessUnauthorizedException(msg));

        Assertions.assertThrows(BusinessUnauthorizedException.class, () -> service.login(request), msg);

    }
}