package br.com.twoapprovalcontentbackend.entrypoint.controllers;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.LoginRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.UserRegisterRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.LoginResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.UserRegisterResponse;
import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
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
public interface UserController {

    @GetMapping(value = "/niches")
    ResponseEntity<ApiResponse<List<NichesEnum>>> niches(HttpServletRequest servletRequest);

    @PostMapping(value = "/register")
    ResponseEntity<ApiResponse<UserRegisterResponse>> register(
            @RequestBody @Valid UserRegisterRequest request,
            HttpServletRequest servletRequest
    );

    @PostMapping(value = "/login")
    ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletRequest servletRequest
    );

}
