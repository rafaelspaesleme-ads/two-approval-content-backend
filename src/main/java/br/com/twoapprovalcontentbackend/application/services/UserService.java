package br.com.twoapprovalcontentbackend.application.services;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.LoginRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.UserRegisterRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.LoginResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.UserRegisterResponse;

public interface UserService {
    UserRegisterResponse register(UserRegisterRequest request);

    LoginResponse login(LoginRequest request);
}
