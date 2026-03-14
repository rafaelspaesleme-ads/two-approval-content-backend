package br.com.twoapprovalcontentbackend.infraestructure.configs.exceptions;

import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessBadRequestException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessConflictException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessForbiddenException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessInternalServerErrorException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessNotFoundException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessUnauthorizedException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessUnprocessableException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.model.ApiErrorMessageResponse;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.model.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AccessException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiErrorResponse> globalBusinessExceptionResponse(BusinessException ex, HttpServletRequest request, String title, HttpStatus status) {

        ApiErrorResponse response = new ApiErrorResponse(
                UUID.randomUUID().toString(),
                title,
                Collections.singletonList(new ApiErrorMessageResponse(ex.getMessage())),
                request.getRequestURI(),
                LocalDateTime.now(),
                ex.isHasMetadata() ? ex.getCause() : null
        );

        log.error("[%s]::[%s]::[%s]::[%s]".formatted(response.timestamp(), response.errorId(), status.name(), response.toString()));

        return new ResponseEntity<>(response, status);

    }

    @ExceptionHandler(BusinessBadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handlerBadRequestException(final BusinessBadRequestException ex, HttpServletRequest request) {
        return this.globalBusinessExceptionResponse(ex, request, "Requisição ruim.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessConflictException.class)
    public ResponseEntity<ApiErrorResponse> handlerConflictException(final BusinessConflictException ex, HttpServletRequest request) {
        return this.globalBusinessExceptionResponse(ex, request, "Conflito na requisição.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handlerForbiddenException(final BusinessForbiddenException ex, HttpServletRequest request) {
        return this.globalBusinessExceptionResponse(ex, request, "Requisição sem acesso.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BusinessNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlerNotFoundException(final BusinessNotFoundException ex, HttpServletRequest request) {
        return this.globalBusinessExceptionResponse(ex, request, "Requisição não encontrada.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessUnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handlerUnauthorizedException(final BusinessUnauthorizedException ex, HttpServletRequest request) {
        return this.globalBusinessExceptionResponse(ex, request, "Requisição não autorizada.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessUnprocessableException.class)
    public ResponseEntity<ApiErrorResponse> handlerUnprocessableException(final BusinessUnprocessableException ex, HttpServletRequest request) {
        return this.globalBusinessExceptionResponse(ex, request, "Requisição com problemas de negócio.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BusinessInternalServerErrorException.class)
    public ResponseEntity<ApiErrorResponse> handlerUnprocessableException(final BusinessInternalServerErrorException ex, HttpServletRequest request) {
        return this.globalBusinessExceptionResponse(ex, request, "Erro no servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationsErros(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ApiErrorMessageResponse> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ApiErrorMessageResponse(e.getField(), e.getDefaultMessage()))
                .toList();

        ApiErrorResponse response = new ApiErrorResponse(
                UUID.randomUUID().toString(),
                "Erro de validação de campo(s).",
                messages,
                request.getRequestURI(),
                LocalDateTime.now(),
                null
        );

        HttpStatus status = HttpStatus.BAD_REQUEST;

        log.error("[%s]::[%s]::[%s]::[%s]".formatted(response.timestamp(), response.errorId(), status.name(), response.toString()));


        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(AccessException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {

        log.error("[%s]::[%s]::[%s]".formatted(LocalDateTime.now(), UUID.randomUUID(), ex.getMessage()));

        return this.globalBusinessExceptionResponse(new BusinessForbiddenException("Você não tem permissão para acessar este recurso"), request, "Acess negado.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {

        log.error("[%s]::[%s]::[%s]".formatted(LocalDateTime.now(), UUID.randomUUID(), ex.getMessage()));

        return this.globalBusinessExceptionResponse(new BusinessForbiddenException("Você não foi autenticado, por isso não autorização para prosseguir com a requisição."), request, "Erro de autenticação.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ApiErrorResponse> handleCredentialsExpiredException(CredentialsExpiredException ex, HttpServletRequest request) {

        log.error("[%s]::[%s]::[%s]".formatted(LocalDateTime.now(), UUID.randomUUID(), ex.getMessage()));

        return this.globalBusinessExceptionResponse(new BusinessForbiddenException("Suas credenciais de acesso expiraram. Tente se conectar novamente."), request, "Login expirado.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericExecption(Exception ex, HttpServletRequest request) {

        log.error("[%s]::[%s]::[%s]".formatted(LocalDateTime.now(), UUID.randomUUID(), ex.getMessage()));

        return this.globalBusinessExceptionResponse(new BusinessInternalServerErrorException("Erro inexperado, tente novamente mais tarde."), request, "Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
