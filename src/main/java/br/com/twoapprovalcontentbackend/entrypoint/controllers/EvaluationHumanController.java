package br.com.twoapprovalcontentbackend.entrypoint.controllers;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.InfosEvaluationResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin("*")
@RequestMapping(value = "/evaluation-human")
public interface EvaluationHumanController {

    @GetMapping(value = "/contents/search")
    ResponseEntity<ApiResponse<List<InfosEvaluationResponse>>> search(
            @RequestParam(value = "evaluationKey") String evaluationKey,
            HttpServletRequest servletRequest
    );

}
