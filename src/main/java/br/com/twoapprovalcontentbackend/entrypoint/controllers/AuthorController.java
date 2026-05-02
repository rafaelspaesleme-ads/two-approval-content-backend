package br.com.twoapprovalcontentbackend.entrypoint.controllers;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.EvaluateContentRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.EvaluateContentResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin("*")
@RequestMapping(value = "/author")
public interface AuthorController {

    @PostMapping(value = "/evaluate")
    ResponseEntity<ApiResponse<EvaluateContentResponse>> evaluate(
            @RequestBody @Valid EvaluateContentRequest request,
            HttpServletRequest servletRequest
    );

}
