package br.com.twoapprovalcontentbackend.infraestructure.configs.securities.fails;

import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessForbiddenException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.model.ApiErrorMessageResponse;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.model.ApiErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class CustomAuthenticationSecurityHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {


        Optional.ofNullable(request.getAttribute("error_apiKey"))
                .map(Object::toString)
                .ifPresentOrElse(msg -> {
                    HttpStatus forbidden = HttpStatus.FORBIDDEN;

                    buildMessageResponse(request, response, msg, forbidden);

                }, () -> buildMessageResponse(request, response, authException.getMessage(), HttpStatus.valueOf(response.getStatus())));


    }

    private static void buildMessageResponse(HttpServletRequest request, HttpServletResponse response, String msg, HttpStatus status) {
        try {
            ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                    UUID.randomUUID().toString(),
                    "Requisição sem acesso.",
                    Collections.singletonList(new ApiErrorMessageResponse(msg)),
                    Optional.ofNullable(request.getAttribute("error_requestURI")).map(Object::toString).orElse(request.getRequestURI()),
                    LocalDateTime.now(),
                    null
            );

            response.setStatus(status.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(apiErrorResponse.toString());

            log.error("[%s]::[%s]::[%s]::[%s]".formatted(apiErrorResponse.timestamp(), apiErrorResponse.errorId(), status, response.toString()));
        } catch (IOException e) {
            throw new BusinessForbiddenException(msg);
        }
    }
}
