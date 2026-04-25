package br.com.twoapprovalcontentbackend.application.mappers.users;

import br.com.twoapprovalcontentbackend.application.mappers.AbstractFunctionsMapper;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.LoginRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.LoginResponse;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;

public class LoginBuilder extends AbstractFunctionsMapper<UserEvaluatorDocument, UserEvaluatorDocument, LoginResponse> {
    public LoginBuilder(LoginRequest request) {
        super.setInput(UserEvaluatorDocument.builder()
                        .id(request.evaluationKey())
                        .secret(request.secretId())
                .build());
    }

    @Override
    public LoginResponse getBuild() {
        return new LoginResponse(
                super.getOutput().getId(),
                super.getOutput().getToken(),
                super.getOutput().getInitialLoginAt(),
                super.getOutput().getExpiresLoginAt()
        );
    }
}
