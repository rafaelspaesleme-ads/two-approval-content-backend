package br.com.twoapprovalcontentbackend.entrypoint.controllers.impl;

import br.com.twoapprovalcontentbackend.application.services.UserService;
import br.com.twoapprovalcontentbackend.entrypoint.controllers.UserController;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.LoginRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.UserRegisterRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.LoginResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.UserRegisterResponse;
import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserControllerImpl extends ResponseControllerImpl implements UserController {

    private final UserService service;

    @Override
    public ResponseEntity<ApiResponse<List<NichesEnum>>> niches(HttpServletRequest servletRequest) {

        return super.setCreate(
                Arrays.stream(NichesEnum.values()).toList(),
                "Nichos de avaliadores",
                Collections.singletonList("Nichos para avaliadores retornados com sucesso!"),
                servletRequest
        );
    }

    @Override
    public ResponseEntity<ApiResponse<UserRegisterResponse>> register(UserRegisterRequest request, HttpServletRequest servletRequest) {

        UserRegisterResponse response = this.service.register(request);

        return super.setCreate(
                response,
                "Registro de Usuário Avaliador",
                Collections.singletonList("Avaliador humano registrado com sucesso! Salve suas credenciais."),
                servletRequest
        );
    }

    @Override
    public ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request, HttpServletRequest servletRequest) {

        LoginResponse response = this.service.login(request);

        return super.setOk(
                response,
                "Login do Usuário Avaliador",
                Collections.singletonList("Login realizado com sucesso!"),
                servletRequest
        );
    }
}
