package br.com.twoapprovalcontentbackend.infraestructure.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AIStatusEvaluationEnum {

    AI_APPROVED,
    NO_ANSWER,
    AI_REJECTED;

    public static AIStatusEvaluationEnum findBy(String status) {
        return Arrays.stream(AIStatusEvaluationEnum.values())
                .filter(s -> s.name().equalsIgnoreCase(status))
                .findFirst()
                .orElse(AIStatusEvaluationEnum.NO_ANSWER);
    }
}
