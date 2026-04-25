package br.com.twoapprovalcontentbackend.infraestructure.gateways.impl;

import br.com.twoapprovalcontentbackend.domain.AuditEntity;
import br.com.twoapprovalcontentbackend.infraestructure.configs.securities.services.JwtService;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessNotFoundException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessUnauthorizedException;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.EvaluatorGateway;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories.UserEvaluatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class EvaluatorGatewayImpl extends PersistGatewayImpl implements EvaluatorGateway {

    @Value(value = "${spring.contexts.security.jwt.expiration.millis}")
    private long expiration;

    private final UserEvaluatorRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public UserEvaluatorDocument register(UserEvaluatorDocument document) {

        String passowrdDecoder = document.getSecret();

        document.setSecret(this.passwordEncoder.encode(passowrdDecoder));

        UserEvaluatorDocument documentSaved = this.repository.save(super.create(document));

        documentSaved.setSecret(passowrdDecoder);

        return documentSaved;
    }

    @Override
    public UserEvaluatorDocument login(UserEvaluatorDocument document) {

        try {
            UserEvaluatorDocument evaluatorDocument = this.repository.findById(document.getId())
                    .filter(AuditEntity::getActive)
                    .orElseThrow(() -> new BusinessNotFoundException("Usuário avaliador não existe."));

            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    evaluatorDocument.getEmail(),
                    document.getSecret()
            ));

            String token = this.jwtService.generateToken(evaluatorDocument.getEmail());

            LocalDateTime now = LocalDateTime.now();

            evaluatorDocument.setSecret(null);
            evaluatorDocument.setToken(token);
            evaluatorDocument.setInitialLoginAt(now);
            evaluatorDocument.setExpiresLoginAt(now.plus(Duration.ofMillis(this.expiration)));

            return evaluatorDocument;
        } catch (Exception e) {
            throw new BusinessUnauthorizedException("Não foi possivel realizar o login. Causa %s".formatted(e.getMessage()));
        }
    }
}
