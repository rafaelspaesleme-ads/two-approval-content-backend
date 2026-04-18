package br.com.twoapprovalcontentbackend.infraestructure.configs.securities;

import br.com.twoapprovalcontentbackend.infraestructure.configs.securities.filters.JwtFilter;
import br.com.twoapprovalcontentbackend.infraestructure.configs.securities.filters.MatchersFilter;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth.
                            requestMatchers(HttpMethod.GET, MatchersFilter.getGets()).permitAll()
                            .requestMatchers(HttpMethod.POST, MatchersFilter.getPosts()).permitAll()
                            .requestMatchers(HttpMethod.PUT, MatchersFilter.getPuts()).permitAll()
                            .requestMatchers(HttpMethod.PATCH, MatchersFilter.getPatchs()).permitAll()
                            .requestMatchers(HttpMethod.DELETE, MatchersFilter.getDeletes()).permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, MatchersFilter.getOptions()).permitAll()
                            .requestMatchers(MatchersFilter.getDocs()).permitAll()
                            .anyRequest()
                            .authenticated())
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        try {
            return configuration.getAuthenticationManager();
        } catch (Exception e) {
            throw new BusinessUnauthorizedException("Falha ao tentar se autenticar nesta API. Causa: %s".formatted(e.getMessage()));
        }
    }

}
