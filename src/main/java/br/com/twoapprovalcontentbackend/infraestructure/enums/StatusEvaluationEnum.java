package br.com.twoapprovalcontentbackend.infraestructure.enums;

import br.com.twoapprovalcontentbackend.infraestructure.exceptions.BusinessNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StatusEvaluationEnum {

    IN_REVIEW("Conteúdo em analise. Em breve você será notificado sobre a avaliação."),
    APPROVED("Conteúdo aprovado!"),
    REJECTED("Conteúdo foi rejeitado. Verifique as justificativas.");

    private final String description;

    StatusEvaluationEnum(String description) {
        this.description = description;
    }

    public static StatusEvaluationEnum findBy(String status) {
        return Arrays.stream(StatusEvaluationEnum.values())
                .filter(s -> s.name().equalsIgnoreCase(status) || s.getDescription().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new BusinessNotFoundException("Status não encontrado."));
    }
}
