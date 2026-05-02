package br.com.twoapprovalcontentbackend.infraestructure.enums;

import lombok.Getter;

@Getter
public enum StatusEvaluationEnum {

    IN_REVIEW("Conteúdo em analise. Em breve você será notificado sobre a avaliação."),
    APPROVED("Conteúdo aprovado!"),
    REJECTED("Conteúdo foi rejeitado. Verifique as justificativas.");

    private final String description;

    StatusEvaluationEnum(String description) {
        this.description = description;
    }
}
