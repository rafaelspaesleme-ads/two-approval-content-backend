package br.com.twoapprovalcontentbackend.infraestructure.configs.securities.filters;

import br.com.twoapprovalcontentbackend.infraestructure.configs.securities.services.ConfigUserDetailsService;
import br.com.twoapprovalcontentbackend.infraestructure.configs.securities.services.JwtService;
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

    @Value(value = "${spring.contexts.api.key}")
    private String apiKey;

    private final JwtService jwtService;

    private final ConfigUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String apiKeyHeader = request.getHeader("apiKey");

        if (Objects.isNull(authorization) || !authorization.startsWith("Bearer ")) {

            if (Objects.nonNull(apiKeyHeader) && !Objects.equals(apiKeyHeader, this.apiKey)) {
                throw new BusinessForbiddenException("Você não tem permissão para prosseguir com essa requisição. Chave de api invalida!");
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
