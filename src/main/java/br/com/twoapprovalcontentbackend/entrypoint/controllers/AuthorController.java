package br.com.twoapprovalcontentbackend.entrypoint.controllers;

import br.com.twoapprovalcontentbackend.entrypoint.dtos.requests.EvaluateContentRequest;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.ApiResponse;
import br.com.twoapprovalcontentbackend.entrypoint.dtos.responses.EvaluateContentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin("*")
@RequestMapping(value = "/author")
@Tag(name = "Author", description = "Endpoints de serviços destinados ao Criador de Conteúdo.")
public interface AuthorController {

    @Operation(
            method = "POST",
            summary = "Envio de conteúdo",
            description = "Serviço de envio de conteúdo para analise."
    )
    @PostMapping(value = "/evaluate")
    ResponseEntity<ApiResponse<EvaluateContentResponse>> evaluate(
            @RequestBody @Valid EvaluateContentRequest request,
            HttpServletRequest servletRequest
    );


    @Operation(
            method = "PATCH",
            summary = "Consultar status de conteúdo",
            description = "Serviço de consulta de status de conteúdo.",
            parameters = {
                    @Parameter(
                            name = "contentId",
                            description = "Identificador do conteúdo enviado para analise.",
                            schema = @Schema(type = "string")
                    )
            }
    )
    @PatchMapping(value = "/content/{contentId}/find-status")
    ResponseEntity<ApiResponse<EvaluateContentResponse>> findStatus(
            @PathVariable(value = "contentId") String contentId,
            HttpServletRequest servletRequest
    );

}
