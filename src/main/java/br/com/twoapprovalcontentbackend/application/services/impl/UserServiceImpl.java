package br.com.twoapprovalcontentbackend.application.services.impl;

import br.com.twoapprovalcontentbackend.application.mappers.users.LoginMapper;
import br.com.twoapprovalcontentbackend.application.mappers.users.UserEvaludationRegisterMapper;
import br.com.twoapprovalcontentbackend.application.services.UserService;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.LoginRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.UserRegisterRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.LoginResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.UserRegisterResponse;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.EvaluatorGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EvaluatorGateway evaluatorGateway;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {

        UserEvaludationRegisterMapper builder = new UserEvaludationRegisterMapper(request);

        builder.setOutput(this.evaluatorGateway::register);

        return builder.getBuild();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        LoginMapper builder = new LoginMapper(request);

        builder.setOutput(this.evaluatorGateway::login);

        return builder.getBuild();
    }
}
