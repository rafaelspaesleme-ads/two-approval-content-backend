package br.com.twoapprovalcontentbackend.entrypoint.controllers;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.UserRegisterRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.UserRegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@CrossOrigin("*")
@RequestMapping(value = "/user")
public interface UserController {

    @PostMapping(value = "/register")
    ResponseEntity<ApiResponse<UserRegisterResponse>> register(
            @RequestBody @Valid UserRegisterRequest request,
            HttpServletRequest servletRequest
    );

}
