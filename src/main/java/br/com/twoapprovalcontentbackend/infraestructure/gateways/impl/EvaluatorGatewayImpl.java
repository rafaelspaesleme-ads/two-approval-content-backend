package br.com.twoapprovalcontentbackend.infraestructure.gateways.impl;

import br.com.twoapprovalcontentbackend.infraestructure.gateways.EvaluatorGateway;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories.UserEvaluatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EvaluatorGatewayImpl extends PersistGatewayImpl implements EvaluatorGateway {

    private final UserEvaluatorRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEvaluatorDocument register(UserEvaluatorDocument document) {

        String passowrdDecoder = document.getSecret();

        document.setSecret(this.passwordEncoder.encode(passowrdDecoder));

        UserEvaluatorDocument documentSaved = this.repository.save(super.create(document));

        documentSaved.setSecret(passowrdDecoder);

        return documentSaved;
    }
}
