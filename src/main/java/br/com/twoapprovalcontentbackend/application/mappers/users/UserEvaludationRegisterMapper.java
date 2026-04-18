package br.com.twoapprovalcontentbackend.application.mappers.users;

import br.com.twoapprovalcontentbackend.application.mappers.AbstractFunctionsMapper;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.UserRegisterRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.UserRegisterResponse;
import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class UserEvaludationRegisterMapper extends AbstractFunctionsMapper<UserEvaluatorDocument, UserEvaluatorDocument, UserRegisterResponse> {


    public UserEvaludationRegisterMapper(UserRegisterRequest request) {

        String secret = "tac_live_v1_" + UUID.randomUUID().toString().replace("-", "");

        super.setInput(UserEvaluatorDocument.builder()
                        .name(request.fullname())
                        .email(request.email())
                        .secret(secret)
                        .niche(NichesEnum.findByAny(request.niche()))
                        .comments(request.comments())
                .build());
    }

    @Override
    public UserRegisterResponse getBuild() {
        return new UserRegisterResponse(
                super.getOutput().getId(),
                super.getOutput().getSecret()
        );
    }
}
