package br.com.twoapprovalcontentbackend.infraestructure.configs.securities.fails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConfigAccessDeniedHandlerFail implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        //TODO: Após criar tratamento de exceptions, vincular esse tratamento auqi.

        response.getWriter().write(accessDeniedException.getLocalizedMessage());

    }
}
