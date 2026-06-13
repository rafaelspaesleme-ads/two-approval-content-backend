package br.com.twoapprovalcontentbackend.infraestructure.configs.securities.filters;

import br.com.twoapprovalcontentbackend.infraestructure.configs.securities.services.ConfigUserDetailsService;
import br.com.twoapprovalcontentbackend.infraestructure.configs.securities.services.JwtService;
import br.com.twoapprovalcontentbackend.infraestructure.enums.HttpHeadersKeyEnum;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessForbiddenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @Value(value = "${spring.contexts.api.key.evaluation}")
    private String apiEvaluationKey;

    @Value(value = "${spring.contexts.api.key.creatorContent}")
    private String apiCreatorContentKey;

    private final JwtService jwtService;

    private final ConfigUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String evaluationKey = request.getHeader(HttpHeadersKeyEnum.API_EVALUATION_KEY.getKey());
        String creatorContentKey = request.getHeader(HttpHeadersKeyEnum.API_CREATOR_CONTENT_KEY.getKey());

        if (Objects.isNull(authorization) || !authorization.startsWith("Bearer ")) {

            String msg = "Você não tem permissão para prosseguir com essa requisição. Chave de api invalida ou não inserida!";
            request.setAttribute("error_apiKey", msg);
            request.setAttribute("error_requestURI", request.getRequestURI());

            if ("/user/register".equals(request.getServletPath()) && !Objects.equals(this.apiEvaluationKey, evaluationKey)) {
                throw new BusinessForbiddenException(msg);
            }

            if (request.getServletPath().contains("/author/") && !Objects.equals(this.apiCreatorContentKey, creatorContentKey)) {
                throw new BusinessForbiddenException(msg);
            }

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);

        String email = this.jwtService.extractEmail(token);

        if (Objects.nonNull(email) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userFound = this.userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userFound.getUsername(), userFound.getPassword(), userFound.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);

    }
}
