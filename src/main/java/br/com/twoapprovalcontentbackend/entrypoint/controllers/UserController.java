package br.com.twoapprovalcontentbackend.entrypoint.controllers;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.LoginRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.UserRegisterRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.LoginResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.UserRegisterResponse;
import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin("*")
@RequestMapping(value = "/user")
@Tag(name = "User", description = "Endpoints de serviços destinados ao usuário administrador da API.")
public interface UserController {

    @Operation(
            method = "GET",
            summary = "Listar nichos",
            description = "Serviço de listagem de nichos para avaliadores e criadores de conteúdo."
    )
    @GetMapping(value = "/niches")
    ResponseEntity<ApiResponse<List<NichesEnum>>> niches(HttpServletRequest servletRequest);

    @Operation(
            method = "POST",
            summary = "Registrar Usuário",
            description = "Serviço de registro de usuário admin, o mesmo que também é o avaliador humano."
    )
    @PostMapping(value = "/register")
    ResponseEntity<ApiResponse<UserRegisterResponse>> register(
            @RequestBody @Valid UserRegisterRequest request,
            HttpServletRequest servletRequest
    );

    @Operation(
            method = "POST",
            summary = "Login",
            description = "Serviço de login do usuário avaliador humano."
    )
    @PostMapping(value = "/login")
    ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletRequest servletRequest
    );

}
