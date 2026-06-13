package br.com.twoapprovalcontentbackend.entrypoint.controllers;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.SendEvaluationRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.InfosEvaluationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin("*")
@RequestMapping(value = "/evaluation-human")
@Tag(name = "EvaluationHuman", description = "Endpoints de serviços destinados ao Avaliador Humano.")
public interface EvaluationHumanController {

    @Operation(
            method = "GET",
            summary = "Busca de conteúdos",
            description = "Serviço de buscar de conteúdos para realizar analise.",
            parameters = {
                    @Parameter(
                            name = "evaluationKey",
                            description = "Chave identificadora do avaliador humano.",
                            schema = @Schema(type = "string")
                    )
            }
    )
    @GetMapping(value = "/contents/search")
    ResponseEntity<ApiResponse<List<InfosEvaluationResponse>>> search(
            @RequestParam(value = "evaluationKey") String evaluationKey,
            HttpServletRequest servletRequest
    );


    @Operation(
            method = "POST",
            summary = "Envio de avaliação",
            description = "Serviço de envio de avaliação final de conteúdo."
    )
    @PostMapping(value = "/send-content-evaluation")
    ResponseEntity<ApiResponse<Void>> sendEvaluation(
            @RequestBody @Valid SendEvaluationRequest request,
            HttpServletRequest servletRequest
    );

}
