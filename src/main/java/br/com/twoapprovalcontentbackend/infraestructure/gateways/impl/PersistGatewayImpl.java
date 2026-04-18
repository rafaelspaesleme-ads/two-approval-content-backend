package br.com.twoapprovalcontentbackend.infraestructure.gateways.impl;

import br.com.twoapprovalcontentbackend.domain.AuditEntity;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessConflictException;
import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

public abstract class PersistGatewayImpl {

    protected <T extends AuditEntity> T create(T document) {
        document.setId(null);
        document.setUpdatedAt(null);

        if (Objects.isNull(document.getActive())) {
            document.setActive(true);
        }

        return document;
    }

    protected <T extends AuditEntity> List<T> create(List<T> list) {

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessNotFoundException("Não há itens a serem registrados.");
        }

        return list.stream().map(this::create).toList();
    }

    protected <T extends AuditEntity> List<T> update(List<T> list) {

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessNotFoundException("Não há itens a serem atualizados.");
        }

        return list.stream().map(this::refresh).toList();
    }

    protected <T extends AuditEntity> T refresh(T document) {

        if (StringUtils.isEmpty(document.getId())) {
            throw new BusinessConflictException("Não é possivel atualizar essas informações sem identificador.");
        }

        if (Objects.isNull(document.getActive())) {
            document.setActive(true);
        }

        return document;
    }

}
