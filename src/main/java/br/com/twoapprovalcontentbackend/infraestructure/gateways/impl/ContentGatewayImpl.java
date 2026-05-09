package br.com.twoapprovalcontentbackend.infraestructure.gateways.impl;

import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessNotFoundException;
import br.com.twoapprovalcontentbackend.infraestructure.gateways.ContentGateway;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories.ContentRepository;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories.UserEvaluatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContentGatewayImpl extends PersistGatewayImpl implements ContentGateway {

    private final ContentRepository repository;
    private final UserEvaluatorRepository userEvaluatorRepository;

    @Override
    public UserEvaluatorDocument getUserEvaluation(NichesEnum niche) {
        return this.userEvaluatorRepository.findByNicheAndActiveTrue(niche)
                .stream()
                .findAny()
                .orElseThrow(() -> new BusinessNotFoundException("Sem avaliador humano para o nicho escolhido."));
    }

    @Override
    public ContentDocument save(ContentDocument document) {
        return this.repository.save(super.create(document));
    }

    @Override
    public ContentDocument findAndUpdateFlagFindStatus(String contentId) {

        ContentDocument contentDocument = this.repository.findById(contentId)
                .orElseThrow(() -> new BusinessNotFoundException("Conteúdo não encontrado"));

        contentDocument.setFlagFindStatus(true);

        return this.repository.save(super.refresh(contentDocument));
    }
}
